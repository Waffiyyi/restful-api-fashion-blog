package com.waffiyyi.fashion.blog.service.serviceImpl;

import com.waffiyyi.fashion.blog.DTOs.CommentDTO;
import com.waffiyyi.fashion.blog.entities.Comment;
import com.waffiyyi.fashion.blog.entities.Design;
import com.waffiyyi.fashion.blog.entities.User;
import com.waffiyyi.fashion.blog.exception.ResourceNotFoundException;
import com.waffiyyi.fashion.blog.exception.UnAuthorizedException;
import com.waffiyyi.fashion.blog.repositories.CommentRepository;
import com.waffiyyi.fashion.blog.repositories.DesignRepository;
import com.waffiyyi.fashion.blog.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
  private final CommentRepository commentRepository;
  private final DesignRepository designRepository;

  @Override
  public CommentDTO findCommentById(Long commentId) {
    Comment comment =
       commentRepository.findById(commentId).orElseThrow(
          () -> new ResourceNotFoundException("Comment not found", HttpStatus.NOT_FOUND));
    return convertToDTO(comment);
  }

  @Override
  public CommentDTO saveComment(CommentDTO commentDTO, User user) {
    Comment comment = convertToEntity(commentDTO, user);
    Comment savedComment = commentRepository.save(comment);
    return convertToDTO(savedComment);
  }

  @Override
  public void deleteComment(Long commentId, User user) {
    Comment comment =
       commentRepository.findById(commentId).orElseThrow(
          () -> new ResourceNotFoundException("Comment not found", HttpStatus.NOT_FOUND));
    if (!Objects.equals(comment.getDesign().getDesignOwner().getId(),
                        user.getId()) || !Objects.equals(
       comment.getCommenter().getId(), user.getId())) {

      throw new UnAuthorizedException("You are not authorized to delete this comment",
                                      HttpStatus.UNAUTHORIZED);
    }
    commentRepository.deleteById(commentId);
  }

  private CommentDTO convertToDTO(Comment comment) {
    CommentDTO commentDTO = new CommentDTO();
    commentDTO.setText(comment.getText());
    commentDTO.setDesign(comment.getDesign().getId());
    commentDTO.setCommenterId(comment.getCommenter().getId());
    return commentDTO;
  }

  private Comment convertToEntity(CommentDTO commentDTO, User user) {
    Comment comment = new Comment();
    comment.setText(commentDTO.getText());
    Design design = designRepository.findById(commentDTO.getDesign())
                       .orElseThrow(() -> new ResourceNotFoundException(
                          "Design not found with id: " + commentDTO.getDesign(),
                          HttpStatus.NOT_FOUND));
    comment.setDesign(design);
    comment.setCommenter(user);
    return comment;
  }

}
