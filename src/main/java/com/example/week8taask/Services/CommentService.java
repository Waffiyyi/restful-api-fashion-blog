package com.example.week8taask.Services;

import com.example.week8taask.DTOs.CommentDTO;
import com.example.week8taask.Entities.Comment;
import com.example.week8taask.Entities.Design;
import com.example.week8taask.Repositories.CommentRepo;
import com.example.week8taask.Repositories.DesignRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private CommentRepo commentRepo;
    private DesignRepo designRepo;
    @Autowired
    CommentService(CommentRepo commentRepo, DesignRepo designRepo){
        this.commentRepo = commentRepo;
        this.designRepo = designRepo;
    }

    public  CommentDTO findCommentById(Long commentId){
        Comment comment = commentRepo.findCommentById(commentId);
        return  convertToDTO(comment);
    }

    public CommentDTO saveComment(CommentDTO commentDTO) {
        Comment comment = convertToEntity(commentDTO);
        Comment savedComment = commentRepo.save(comment);
        return convertToDTO(savedComment);
    }

//    private Comment getCommentById(Long commentId) {
//        return commentRepo.findById(commentId)
//                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));
//    }

    public void deleteComment(Long commentId) {
        commentRepo.deleteById(commentId);
    }
    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setText(comment.getText());
        commentDTO.setDesign(comment.getDesign().getId());
        return commentDTO;
    }

    private Comment convertToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        Design design = designRepo.findById(commentDTO.getDesign())
                .orElseThrow(() -> new EntityNotFoundException("Design not found with id: " + commentDTO.getDesign()));
        comment.setDesign(design);
        return comment;
    }

}
