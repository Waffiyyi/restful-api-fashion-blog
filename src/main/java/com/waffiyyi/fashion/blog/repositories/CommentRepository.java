package com.waffiyyi.fashion.blog.repositories;

import com.waffiyyi.fashion.blog.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
//    List<Comment> findByDesign_Id(Long designId);
    Comment findCommentById(Long commentId);
}
