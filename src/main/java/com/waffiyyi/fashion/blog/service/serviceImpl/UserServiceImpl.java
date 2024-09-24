package com.waffiyyi.fashion.blog.service.serviceImpl;

import com.waffiyyi.fashion.blog.DTOs.AuthResponse;
import com.waffiyyi.fashion.blog.DTOs.DesignDTO;
import com.waffiyyi.fashion.blog.DTOs.LoginDTO;
import com.waffiyyi.fashion.blog.DTOs.UserDTO;
import com.waffiyyi.fashion.blog.entities.Category;
import com.waffiyyi.fashion.blog.entities.Design;
import com.waffiyyi.fashion.blog.entities.User;
import com.waffiyyi.fashion.blog.repositories.DesignRepository;
import com.waffiyyi.fashion.blog.repositories.UserRepository;
import com.waffiyyi.fashion.blog.config.JwtProvider;
import com.waffiyyi.fashion.blog.exception.BadRequestException;
import com.waffiyyi.fashion.blog.exception.UserNotFoundException;
import com.waffiyyi.fashion.blog.service.UserService;
import com.waffiyyi.fashion.blog.validations.EmailValidator;
import com.waffiyyi.fashion.blog.validations.PasswordValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private final CustomUserDetailsService customUserDetailsService;
  private final DesignRepository designRepository;


  @Override
  public ResponseEntity<AuthResponse> register(User user) {
    if (!EmailValidator.isValid(user.getEmail())) {
      throw new BadRequestException("Invalid email format", HttpStatus.BAD_REQUEST);
    }

    if (!PasswordValidator.isValid(user.getPassword())) {
      throw new BadRequestException("Invalid password format, password must include at " +
                                       "least one uppercase letter, one lowercase letter, and one digit ",
                                    HttpStatus.BAD_REQUEST);
    }

    User isEmailExist = userRepository.findByEmail(user.getEmail());
    if (isEmailExist != null) {
      throw new BadRequestException("Email is already used with another account",
                                    HttpStatus.BAD_REQUEST);
    }
    if (user.getFirstName() == null) {
      throw new BadRequestException("Please enter your first name",
                                    HttpStatus.BAD_REQUEST);

    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User savedUser = userRepository.save(user);
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(
       savedUser.getEmail());

    Authentication authentication = new UsernamePasswordAuthenticationToken(
       userDetails, null, userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = jwtProvider.generateToken(authentication);

    AuthResponse response = AuthResponse.builder()
                               .jwt(jwt)
                               .message("Register success")
                               .build();

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @Override

  public ResponseEntity<AuthResponse> loginUser(LoginDTO req) {
    String username = req.getUsername();
    String password = req.getPassword();

    Authentication authentication = authenticate(username, password);

    String jwt = jwtProvider.generateToken(authentication);

    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

    String role =
       authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

    AuthResponse response = AuthResponse.builder()
                               .jwt(jwt)
                               .message("Login success")
                               .build();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @Override

  public User findUserByJWTToken(String jwt) {
    String email = jwtProvider.getEmailFromJwtToken(jwt);
    User user = findUserByEmail(email);
    return user;
  }

  @Override
  public User findUserByEmail(String email) {
    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new UserNotFoundException("User not found", HttpStatus.NOT_FOUND);
    }
    return user;
  }

  @Override
  @Transactional
  public String followUser(User user, Long followerId) {


    User follower = userRepository.findById(followerId)
                       .orElseThrow(() -> new UserNotFoundException("Follower not found",
                                                                    HttpStatus.NOT_FOUND));

    user.getFollowers().add(follower);
    userRepository.save(user);
    return "Successfully followed user";
  }


  @Override
  public Set<UserDTO> viewFollowers(User user) {
    return user.getFollowers().stream()
              .map(follower -> new UserDTO(
                 follower.getId(),
                 follower.getFirstName(),
                 follower.getLastName(),
                 follower.getProfilePicture()
              ))
              .collect(Collectors.toSet());
  }

  private Authentication authenticate(String username, String password) {
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

    if (userDetails == null) {
      throw new BadCredentialsException("Invalid username....");
    }
    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
      throw new BadCredentialsException("Invalid password....");
    }
    return new UsernamePasswordAuthenticationToken(userDetails, null,
                                                   userDetails.getAuthorities());
  }

  @Override
  public List<DesignDTO> getRecommendations(User user) {
    Set<Category> likedCategories = user.getLikedDesigns().stream()
                                       .map(Design::getCategory)
                                       .collect(Collectors.toSet());
    return designRepository.findTop10ByCategoryInOrderByLikesCountDesc(likedCategories)
              .stream()
              .map(this::convertToDto)
              .collect(Collectors.toList());
  }

  @Override
  public List<DesignDTO> getPopularDesigns() {
    return designRepository.findTop10ByOrderByLikesCountDesc()
              .stream()
              .map(this::convertToDto)
              .collect(Collectors.toList());
  }

  @Override
  public List<DesignDTO> getTrendingDesigns() {
    LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
    return designRepository.findTrendingDesigns(oneWeekAgo)
              .stream()
              .map(this::convertToDto)
              .collect(Collectors.toList());
  }


  private DesignDTO convertToDto(Design design) {
    DesignDTO dto = new DesignDTO();
    dto.setId(design.getId());
    dto.setName(design.getName());
    dto.setDescription(design.getDescription());
    dto.setCategory(design.getCategory().getId());
    dto.setLikesCount(design.getLikesCount());
    dto.setOwnerId(design.getDesignOwner().getId());
    return dto;
  }
}
