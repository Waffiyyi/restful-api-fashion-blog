package com.waffiyyi.fashion.blog.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;
  private String firstName;
  private String lastName;
  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private String email;
  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private String gender;
  private String profilePicture;
  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private Long followerCount;
  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private Long followingCount;
}
