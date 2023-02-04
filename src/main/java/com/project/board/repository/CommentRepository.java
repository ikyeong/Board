package com.project.board.repository;

import com.project.board.domain.Comment;
import com.project.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostOrderById(Post post);
}
