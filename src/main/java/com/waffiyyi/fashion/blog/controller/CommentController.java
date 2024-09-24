package com.waffiyyi.fashion.blog.controller;

import com.waffiyyi.fashion.blog.DTOs.CommentDTO;
import com.waffiyyi.fashion.blog.entities.User;
import com.waffiyyi.fashion.blog.service.CommentService;
import com.waffiyyi.fashion.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;
  private final UserService userService;


  @PostMapping
  public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO,
                                                  @RequestHeader("Authorization")
                                                  String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    CommentDTO createdComment = commentService.saveComment(commentDTO, user);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
  }

  @GetMapping("/{commentId}")
  public ResponseEntity<CommentDTO> findCommentById(@PathVariable Long commentId) {
    CommentDTO commentDTO = commentService.findCommentById(commentId);
    return ResponseEntity.ok(commentDTO);
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                            @RequestHeader("Authorization")
                                            String jwt) {
    User user = userService.findUserByJWTToken(jwt);
    commentService.deleteComment(commentId, user);
    return ResponseEntity.noContent().build();
  }

}
