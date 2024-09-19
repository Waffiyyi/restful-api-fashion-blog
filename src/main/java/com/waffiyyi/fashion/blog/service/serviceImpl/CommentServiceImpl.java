package com.waffiyyi.fashion.blog.service.serviceImpl;

import com.waffiyyi.fashion.blog.DTOs.CommentDTO;
import com.waffiyyi.fashion.blog.entities.Comment;
import com.waffiyyi.fashion.blog.entities.Design;
import com.waffiyyi.fashion.blog.repositories.CommentRepository;
import com.waffiyyi.fashion.blog.repositories.DesignRepository;
import com.waffiyyi.fashion.blog.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
  private final CommentRepository commentRepository;
  private final DesignRepository designRepository;

  @Override
  public CommentDTO findCommentById(Long commentId) {
    Comment comment = commentRepository.findCommentById(commentId);
    return convertToDTO(comment);
  }

  @Override
  public CommentDTO saveComment(CommentDTO commentDTO) {
    Comment comment = convertToEntity(commentDTO);
    Comment savedComment = commentRepository.save(comment);
    return convertToDTO(savedComment);
  }

  //    private Comment getCommentById(Long commentId) {
  //        return commentRepo.findById(commentId)
  //                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));
  //    }
  @Override
  public void deleteComment(Long commentId) {
    commentRepository.deleteById(commentId);
  }

  private CommentDTO convertToDTO(Comment comment) {
    CommentDTO commentDTO = new CommentDTO();
    commentDTO.setText(comment.getText());
    commentDTO.setDesign(comment.getDesign().getId());
    return commentDTO;
  }

  private Comment convertToEntity(CommentDTO commentDTO) {
    Comment comment = new Comment();
    comment.setText(commentDTO.getText());
    Design design = designRepository.findById(commentDTO.getDesign())
                       .orElseThrow(() -> new EntityNotFoundException(
                          "Design not found with id: " + commentDTO.getDesign()));
    comment.setDesign(design);
    return comment;
  }

}
