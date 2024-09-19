package com.waffiyyi.fashion.blog.service;

import com.waffiyyi.fashion.blog.DTOs.AuthResponse;
import com.waffiyyi.fashion.blog.DTOs.LoginDTO;
import com.waffiyyi.fashion.blog.entities.User;
import org.springframework.http.ResponseEntity;


public interface UserService {
  ResponseEntity<AuthResponse> register(User user);


  ResponseEntity<AuthResponse> loginUser(LoginDTO req);


  User findUserByJWTToken(String jwt);

  User findUserByEmail(String email);

}
