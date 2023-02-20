package com.project.board.repository;

import com.project.board.domain.Heart;
import com.project.board.domain.Post;
import com.project.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findByUserAndPost(User user, Post post);
}
