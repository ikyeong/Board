package com.project.board.service;

import com.project.board.domain.Comment;
import com.project.board.domain.Post;
import com.project.board.domain.User;
import com.project.board.domain.dto.CommentDto;
import com.project.board.repository.CommentRepository;
import com.project.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentDto.Response save(CommentDto.Request dto, Long id, User loginUser) {
        Post post = postRepository.findById(id).get();
        dto.setUser(loginUser);
        dto.setPost(post);
        dto.setTimeStamp(LocalDate.now());
        Comment comment = dto.toEntity();
        post.addComment(comment);
        return new CommentDto.Response(commentRepository.save(comment));
    }

    @Transactional
    public List<CommentDto.Response> findAll(Long id) {
        Post post = postRepository.findById(id).get();
        List<Comment> comments = post.getComments();
        return comments.stream().map(comment -> new CommentDto.Response(comment)).toList();
    }

    @Transactional
    public void update(CommentDto.Request dto, Long id) {
        Comment comment = commentRepository.findById(id).get();
        comment.update(dto.getContents());
    }

    @Transactional
    public void delete(Long id) {
        commentRepository.delete(commentRepository.findById(id).get());
    }
}
