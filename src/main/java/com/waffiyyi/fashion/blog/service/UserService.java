package com.waffiyyi.fashion.blog.service;

import com.waffiyyi.fashion.blog.DTOs.*;
import com.waffiyyi.fashion.blog.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;


public interface UserService {
  ResponseEntity<AuthResponse> register(AuthRequestDTO user);
  ResponseEntity<AuthResponse> loginUser(LoginDTO req);
  User findUserByJWTToken(String jwt);
  User findUserByEmail(String email);
  String followUser(User userId, Long followerId);
  Set<ViewFollowerResponseDTO> viewFollowers(User user);
  Set<ViewFollowerResponseDTO> viewFollowing(User user);
  List<DesignDTO> getRecommendations(User user);
  List<DesignDTO> getPopularDesigns();
  List<DesignDTO> getTrendingDesigns();

  UserDTO viewProfile(User user);
  UserDTO updateProfile(UserDTO userDTO, User user);

}
