package com.waffiyyi.fashion.blog.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewFollowerResponseDTO {
  private String firstName;
  private String lastName;
  private String email;
  private String gender;
  private String profilePicture;
}
