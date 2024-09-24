package com.waffiyyi.fashion.blog.service;

import com.waffiyyi.fashion.blog.DTOs.AuthResponse;
import com.waffiyyi.fashion.blog.DTOs.DesignDTO;
import com.waffiyyi.fashion.blog.DTOs.LoginDTO;
import com.waffiyyi.fashion.blog.DTOs.UserDTO;
import com.waffiyyi.fashion.blog.entities.Design;
import com.waffiyyi.fashion.blog.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;


public interface UserService {
  ResponseEntity<AuthResponse> register(User user);
  ResponseEntity<AuthResponse> loginUser(LoginDTO req);
  User findUserByJWTToken(String jwt);
  User findUserByEmail(String email);
  String followUser(User userId, Long followerId);
  Set<UserDTO> viewFollowers(User user);
  List<DesignDTO> getRecommendations(User user);
  List<DesignDTO> getPopularDesigns();
  List<DesignDTO> getTrendingDesigns();

}
