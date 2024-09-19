package com.waffiyyi.fashion.blog.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String jwt;
    private String message;

//    private USER_ROLE role;
}
