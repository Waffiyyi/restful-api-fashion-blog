package com.waffiyyi.fashion.blog.service.serviceImpl;


import com.waffiyyi.fashion.blog.entities.User;
import com.waffiyyi.fashion.blog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username)
     throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found with email " + username);
    }
//    USER_ROLE role = user.getRole();
    List<GrantedAuthority> authorities = new ArrayList<>();
//    authorities.add(new SimpleGrantedAuthority(role.toString()));
    return new org.springframework.security.core.userdetails.User(user.getEmail(),
                                                                  user.getPassword(),
                                                                  authorities);
  }
}
