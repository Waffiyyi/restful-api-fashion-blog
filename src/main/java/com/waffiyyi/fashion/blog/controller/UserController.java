package com.waffiyyi.fashion.blog.controller;


import com.waffiyyi.fashion.blog.DTOs.UserDTO;
import com.waffiyyi.fashion.blog.entities.User;
import com.waffiyyi.fashion.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;


  @PostMapping("/follow-user")
  public ResponseEntity<String> followUser(@RequestHeader("Authorization") String jwt,
                                           @RequestParam Long followerId) {
    User user = userService.findUserByJWTToken(jwt);
    return new ResponseEntity<>(userService.followUser(user, followerId),
                                HttpStatus.OK);
  }

  @PostMapping("/view-followers")
  public ResponseEntity<Set<UserDTO>> viewFollowers(
     @RequestHeader("Authorization") String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    return new ResponseEntity<>(userService.viewFollowers(user),
                                HttpStatus.OK);
  }


}
