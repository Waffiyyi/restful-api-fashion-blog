package com.waffiyyi.fashion.blog.controller;

import com.waffiyyi.fashion.blog.DTOs.AuthRequestDTO;
import com.waffiyyi.fashion.blog.DTOs.AuthResponse;
import com.waffiyyi.fashion.blog.DTOs.ErrorResponse;
import com.waffiyyi.fashion.blog.DTOs.LoginDTO;
import com.waffiyyi.fashion.blog.entities.User;
import com.waffiyyi.fashion.blog.exception.BadRequestException;
import com.waffiyyi.fashion.blog.exception.ResourceNotFoundException;
import com.waffiyyi.fashion.blog.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "This controller manages Auth operations")

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

   private final UserService userService;

   @Operation(summary = "Sign Up", description = "Sign Up",
              security = @SecurityRequirement(name = ""))
   @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = AuthResponse.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = ErrorResponse.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ErrorResponse.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!", content =
     @Content(schema = @Schema(implementation = ErrorResponse.class)))})

   @PostMapping("/signup")
   public ResponseEntity<AuthResponse> signup(@RequestBody AuthRequestDTO user) {
      return userService.register(user);
   }

   @Operation(summary = "Login", description = "Login", security = @SecurityRequirement(name = ""))
   @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = AuthResponse.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = ErrorResponse.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ErrorResponse.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!", content =
     @Content(schema = @Schema(implementation = ErrorResponse.class)))})
   @PostMapping("/login")
   public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginRequest) {
      return userService.loginUser(loginRequest);
   }

}
