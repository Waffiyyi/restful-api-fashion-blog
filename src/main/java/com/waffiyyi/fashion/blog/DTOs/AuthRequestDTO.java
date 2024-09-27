package com.waffiyyi.fashion.blog.DTOs;

import lombok.Data;

@Data
public class AuthRequestDTO {
  private String firstName;
  private String lastName;

  private String email;
  private String gender;
  private String password;
  private String profilePicture;
}
