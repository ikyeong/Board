package com.project.board.repository;

import com.project.board.domain.Post;
import com.project.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.title like %:keyword% order by p.id desc")
    List<Post> findByTitleContainingByOrderByIdDesc(String keyword);
    List<Post> findByUser(User user);
    List<Post> findAllByOrderByIdDesc();
}
