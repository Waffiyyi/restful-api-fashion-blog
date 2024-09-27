package com.waffiyyi.fashion.blog.service.serviceImpl;

import com.waffiyyi.fashion.blog.DTOs.*;
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
import java.util.Objects;
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
  public ResponseEntity<AuthResponse> register(AuthRequestDTO user) {
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
    User savedUser = userRepository.save(convertToUser(user));
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
    User userToFollow = userRepository.findById(followerId)
                           .orElseThrow(() -> new UserNotFoundException("User not found",
                                                                        HttpStatus.NOT_FOUND));
    if (Objects.equals(userToFollow.getId(), user.getId())) {
      throw new BadRequestException("You cant follow yourself", HttpStatus.BAD_REQUEST);
    }
    if(user.getFollowing().contains(userToFollow)){
      throw new BadRequestException("Already following user", HttpStatus.BAD_REQUEST);
    }

    userToFollow.getFollowers().add(user);
    userToFollow.setFollowerCount(userToFollow.getFollowerCount() + 1);
    userRepository.save(userToFollow);
    user.getFollowing().add(userToFollow);
    user.setFollowingCount(user.getFollowingCount() + 1);
    userRepository.save(user);
    return "Successfully followed user";
  }


  @Override
  public Set<ViewFollowerResponseDTO> viewFollowers(User user) {
    return user.getFollowers().stream()
              .map(follower -> new ViewFollowerResponseDTO(
                 follower.getFirstName(),
                 follower.getLastName(),
                 follower.getProfilePicture(),
                 follower.getGender(),
                 follower.getEmail()
              ))
              .collect(Collectors.toSet());
  }

  @Override
  public Set<ViewFollowerResponseDTO> viewFollowing(User user) {
    return user.getFollowing().stream()
              .map(following -> new ViewFollowerResponseDTO(
                 following.getFirstName(),
                 following.getLastName(),
                 following.getProfilePicture(),
                 following.getGender(),
                 following.getEmail()
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

  @Override
  public UserDTO viewProfile(User user) {
    return convertToUserDto(user);
  }

  @Override
  public UserDTO updateProfile(UserDTO userDTO, User user) {
    if (userDTO.getProfilePicture() != null) {
      user.setProfilePicture(userDTO.getProfilePicture());
    }
    if (userDTO.getFirstName() != null) {
      user.setFirstName(userDTO.getFirstName());
    }
    if (userDTO.getLastName() != null) {
      user.setLastName(userDTO.getLastName());
    }
    userRepository.save(user);
    return convertToUserDto(user);
  }


  private DesignDTO convertToDto(Design design) {
    DesignDTO dto = new DesignDTO();
    dto.setId(design.getId());
    dto.setName(design.getName());
    dto.setDescription(design.getDescription());
    if (design.getCategory() != null) {
      dto.setCategoryId(design.getCategory().getId());
    } else {
      dto.setCategoryId(null);

    }
    dto.setLikesCount(design.getLikesCount());
    dto.setOwnerId(design.getDesignOwner().getId());
    return dto;
  }

  private User convertToUser(AuthRequestDTO authRequestDTO) {
    User user = new User();
    user.setEmail(authRequestDTO.getEmail());
    user.setGender(authRequestDTO.getGender());
    user.setFirstName(authRequestDTO.getFirstName());
    user.setLastName(authRequestDTO.getLastName());
    user.setPassword(authRequestDTO.getPassword());
    user.setProfilePicture(authRequestDTO.getProfilePicture());
    return user;
  }

  private UserDTO convertToUserDto(User user) {
    UserDTO userDTO = new UserDTO();
    userDTO.setId(user.getId());
    userDTO.setEmail(user.getEmail());
    userDTO.setGender(user.getGender());
    userDTO.setFirstName(user.getFirstName());
    userDTO.setLastName(user.getLastName());
    userDTO.setProfilePicture(user.getProfilePicture());
    userDTO.setFollowerCount(user.getFollowerCount());
    userDTO.setFollowingCount(user.getFollowingCount());
    return userDTO;
  }
}
