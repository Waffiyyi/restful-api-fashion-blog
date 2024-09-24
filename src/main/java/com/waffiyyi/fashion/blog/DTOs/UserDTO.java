package com.waffiyyi.fashion.blog.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  private Long id;
  private String firstName;
  private String lastName;
  private String profilePicture;
}
