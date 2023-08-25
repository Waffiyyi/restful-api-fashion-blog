package com.example.week8taask.Repositories;

import com.example.week8taask.Entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
//    List<Comment> findByDesign_Id(Long designId);
    Comment findCommentById(Long commentId);
}
