package com.waffiyyi.fashion.blog.controller;

import com.waffiyyi.fashion.blog.DTOs.CategoryDTO;
import com.waffiyyi.fashion.blog.DTOs.CommentDTO;
import com.waffiyyi.fashion.blog.DTOs.ErrorResponse;
import com.waffiyyi.fashion.blog.entities.User;
import com.waffiyyi.fashion.blog.exception.BadRequestException;
import com.waffiyyi.fashion.blog.exception.ResourceNotFoundException;
import com.waffiyyi.fashion.blog.service.CommentService;
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

@Tag(name = "Comment", description = "This controller manages Comment operations")

@RestController
@RequestMapping("api/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;
  private final UserService userService;

  @Operation(summary = "Create Comment",
             description = "Used to create comment on a design")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = CommentDTO.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = ErrorResponse.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ErrorResponse.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!", content =
     @Content(schema = @Schema(implementation = ErrorResponse.class)))})
  @PostMapping
  public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO,
                                                  @RequestHeader("Authorization")
                                                  String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    CommentDTO createdComment = commentService.saveComment(commentDTO, user);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
  }

  @Operation(summary = "Get Comment",
             description = "Used to get comment of the specified ID")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = CommentDTO.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = ErrorResponse.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ErrorResponse.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!", content =
     @Content(schema = @Schema(implementation = ErrorResponse.class)))})
  @GetMapping("/{commentId}")
  public ResponseEntity<CommentDTO> findCommentById(@PathVariable Long commentId) {
    CommentDTO commentDTO = commentService.findCommentById(commentId);
    return ResponseEntity.ok(commentDTO);
  }

  @Operation(summary = "Get Comment", description = "Used to get all comment of a design")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema(implementation = CommentDTO.class))),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = ErrorResponse.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ErrorResponse.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!", content =
     @Content(schema = @Schema(implementation = ErrorResponse.class)))})
  @GetMapping("/get-all/{designId}")
  public ResponseEntity<List<CommentDTO>> getAllComment(@PathVariable Long designId) {
    List<CommentDTO> commentDTO = commentService.getAllDesignComment(designId);
    return ResponseEntity.ok(commentDTO);
  }

  @Operation(summary = "Delete Comment", description = "Used to delete comment")
  @ApiResponses(value = {
     @ApiResponse(responseCode = "200", description = "Successful", content =
     @Content(schema = @Schema())),
     @ApiResponse(responseCode = "400", description = "Bad Request", content =
     @Content(schema = @Schema(implementation = ErrorResponse.class))),
     @ApiResponse(responseCode = "404", description = "No Record Found", content =
     @Content(schema = @Schema(implementation = ErrorResponse.class))),
     @ApiResponse(responseCode = "500", description = "Internal Server Error!", content =
     @Content(schema = @Schema(implementation = ErrorResponse.class)))})
  @DeleteMapping("/{commentId}")
  public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                            @RequestHeader("Authorization")
                                            String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    commentService.deleteComment(commentId, user);
    return ResponseEntity.noContent().build();
  }

}
