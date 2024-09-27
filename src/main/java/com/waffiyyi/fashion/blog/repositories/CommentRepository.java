package com.waffiyyi.fashion.blog.repositories;

import com.waffiyyi.fashion.blog.entities.Comment;
import com.waffiyyi.fashion.blog.entities.Design;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
//    List<Comment> findByDesign_Id(Long designId);
    Comment findCommentById(Long commentId);
    List<Comment> getAllByDesign(Design design);
}
