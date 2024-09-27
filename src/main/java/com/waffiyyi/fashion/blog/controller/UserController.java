package com.waffiyyi.fashion.blog.controller;


import com.waffiyyi.fashion.blog.DTOs.DesignDTO;
import com.waffiyyi.fashion.blog.DTOs.UserDTO;
import com.waffiyyi.fashion.blog.DTOs.ViewFollowerResponseDTO;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Tag(name = "User", description = "This controller manages User operations")
@OpenAPIDefinition(info = @Info(title = "USER CONTROLLER", version = "1.0",
                                description = "USER SERVICE API documentation"))
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @Operation(summary = "Follow User", description = "Used to follow a user")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = String.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = BadRequestException.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!")
  })
  @PostMapping("/follow-user")
  public ResponseEntity<String> followUser(@RequestHeader("Authorization") String jwt,
                                           @RequestParam Long userToFollowId) {
    User user = userService.findUserByJWTToken(jwt);
    return new ResponseEntity<>(userService.followUser(user, userToFollowId),
                                HttpStatus.OK);
  }

  @Operation(summary = "View followers", description = "Used to view followers")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = UserDTO.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = BadRequestException.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!")
  })
  @GetMapping("/view-followers")
  public ResponseEntity<Set<ViewFollowerResponseDTO>> viewFollowers(
     @RequestHeader("Authorization") String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    return new ResponseEntity<>(userService.viewFollowers(user), HttpStatus.OK);
  }

  @Operation(summary = "View followers", description = "Used to view followers")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = UserDTO.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = BadRequestException.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!")
  })
  @GetMapping("/view-following")
  public ResponseEntity<Set<ViewFollowerResponseDTO>> viewFollowing(
     @RequestHeader("Authorization") String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    return new ResponseEntity<>(userService.viewFollowing(user), HttpStatus.OK);
  }

  @Operation(summary = "Get recommendations",
             description = "Used to get design recommendations based on user activity")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = DesignDTO.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = BadRequestException.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!")
  })
  @PostMapping("/get-recommendations")
  public ResponseEntity<List<DesignDTO>> getRecommendations(
     @RequestHeader("Authorization") String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    return new ResponseEntity<>(userService.getRecommendations(user), HttpStatus.OK);
  }

  @Operation(summary = "View popular designs",
             description = "Used to view popular designs")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = DesignDTO.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = BadRequestException.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!")
  })
  @PostMapping("/view-popular-design")
  public ResponseEntity<List<DesignDTO>> viewPopularDesign() {
    return new ResponseEntity<>(userService.getPopularDesigns(), HttpStatus.OK);
  }


  @Operation(summary = "View trending designs",
             description = "Used to view trending designs")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = DesignDTO.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = BadRequestException.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!")
  })
  @PostMapping("/view-trending-design")
  public ResponseEntity<List<DesignDTO>> viewTrendingDesigns() {
    return new ResponseEntity<>(userService.getTrendingDesigns(), HttpStatus.OK);
  }


  @Operation(summary = "View Profile",
             description = "Used to view user profile")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = UserDTO.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = BadRequestException.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!")
  })
  @PostMapping("/view-profile")
  public ResponseEntity<UserDTO> viewProfile(
     @RequestHeader("Authorization") String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    return new ResponseEntity<>(userService.viewProfile(user), HttpStatus.OK);
  }

  @Operation(summary = "Update Profile",
             description = "Used to update user profile")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = UserDTO.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = BadRequestException.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!")
  })
  @PutMapping("/update-profile")
  public ResponseEntity<UserDTO> updateProfile(
     @RequestBody UserDTO userDTO,
     @RequestHeader("Authorization") String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    return new ResponseEntity<>(userService.updateProfile(userDTO, user), HttpStatus.OK);
  }

}
