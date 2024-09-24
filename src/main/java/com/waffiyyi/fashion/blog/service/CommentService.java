package com.waffiyyi.fashion.blog.service;

import com.waffiyyi.fashion.blog.DTOs.CommentDTO;
import com.waffiyyi.fashion.blog.entities.Comment;
import com.waffiyyi.fashion.blog.entities.User;

public interface CommentService {
  CommentDTO findCommentById(Long commentId);

  CommentDTO saveComment(CommentDTO commentDTO, User user);


  void deleteComment(Long commentId, User user);
}
