package com.waffiyyi.fashion.blog.service;

import com.waffiyyi.fashion.blog.DTOs.CommentDTO;
import com.waffiyyi.fashion.blog.entities.Comment;

public interface CommentService {
  CommentDTO findCommentById(Long commentId);

  CommentDTO saveComment(CommentDTO commentDTO);


  void deleteComment(Long commentId);
}
