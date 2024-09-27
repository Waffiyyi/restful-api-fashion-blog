package com.waffiyyi.fashion.blog.service;

import com.waffiyyi.fashion.blog.DTOs.CommentDTO;
import com.waffiyyi.fashion.blog.entities.User;

import java.util.List;

public interface CommentService {
  CommentDTO findCommentById(Long commentId);

  CommentDTO saveComment(CommentDTO commentDTO, User user);


  void deleteComment(Long commentId, User user);

  List<CommentDTO> getAllDesignComment(Long designId);
}
