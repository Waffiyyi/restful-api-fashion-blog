package com.waffiyyi.fashion.blog.controller;

import com.waffiyyi.fashion.blog.DTOs.AuthResponse;
import com.waffiyyi.fashion.blog.DTOs.LoginDTO;
import com.waffiyyi.fashion.blog.entities.User;
import com.waffiyyi.fashion.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> createCategory(@RequestBody User user) {
    return userService.register(user);
  }


  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginRequest) {
    return userService.loginUser(loginRequest);
  }

}
