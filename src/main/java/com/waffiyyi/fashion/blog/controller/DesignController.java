package com.waffiyyi.fashion.blog.controller;

import com.waffiyyi.fashion.blog.DTOs.CommentDTO;
import com.waffiyyi.fashion.blog.DTOs.DesignDTO;
import com.waffiyyi.fashion.blog.entities.User;
import com.waffiyyi.fashion.blog.exception.BadRequestException;
import com.waffiyyi.fashion.blog.exception.ResourceNotFoundException;
import com.waffiyyi.fashion.blog.service.DesignService;
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

@Tag(name = "Design", description = "This controller manages Design operations")

@RestController
@RequestMapping("api/designs")
@RequiredArgsConstructor
public class DesignController {
  private final DesignService designService;
  private final UserService userService;

  @Operation(summary = "Create Design", description = "Used to create a design")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = DesignDTO.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = BadRequestException.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!")
  })
  @PostMapping
  public ResponseEntity<DesignDTO> createDesign(@RequestBody DesignDTO designDTO,
                                                @RequestHeader("Authorization")
                                                String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    DesignDTO createDesign = designService.saveDesign(designDTO, user);
    return ResponseEntity.status(HttpStatus.CREATED).body(createDesign);
  }


  @Operation(summary = "Update Design", description = "Used to update a design")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = DesignDTO.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = BadRequestException.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!")
  })
  @PutMapping("/{designId}")
  public ResponseEntity<DesignDTO> updateDesign(@RequestBody DesignDTO designDTO,
                                                @PathVariable Long designId,
                                                @RequestHeader("Authorization")
                                                String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    DesignDTO createDesign = designService.updateDesign(designDTO,designId, user);
    return ResponseEntity.status(HttpStatus.OK).body(createDesign);
  }
  @Operation(summary = "Get Design",
             description = "Used to get a design of the specified ID")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = DesignDTO.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = BadRequestException.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!")
  })
  @GetMapping("/{designId}")
  public ResponseEntity<DesignDTO> getDesignById(@PathVariable Long designId) {
    DesignDTO designDTO = designService.findDesignById(designId);
    return ResponseEntity.ok(designDTO);
  }

  @Operation(summary = "Delete Design", description = "Used to delete a design")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema())),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = BadRequestException.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!")
  })
  @DeleteMapping("/{designId}")
  public ResponseEntity<Void> deleteDesign(@PathVariable Long designId,
                                           @RequestHeader("Authorization")
                                           String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    designService.deleteDesign(designId, user);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "like/unlike Design",
             description = "Used to like or unlike a design")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = String.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = BadRequestException.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ResourceNotFoundException.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!")
  })
  @PostMapping("/{designId}/toggle-like")
  public ResponseEntity<String> toggleLikeDesign(@PathVariable Long designId,
                                                 @RequestHeader("Authorization")
                                                 String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    return new ResponseEntity<>(designService.toggleLikeDesign(designId, user),
                                HttpStatus.OK);
  }

}
