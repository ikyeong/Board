package com.project.board.service;

import com.project.board.domain.Post;
import com.project.board.domain.User;
import com.project.board.domain.dto.PostDto;
import com.project.board.repository.PostRepository;
import com.project.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostDto.Response save(PostDto.Request dto, User loginUser) {
        Post post = dto.toEntity();
        post.setUser(loginUser);
        post.setTimeStamp(LocalDate.now());
        loginUser.addPost(post);
        return new PostDto.Response(postRepository.save(post));
    }

    @Transactional( readOnly = true)
    public PostDto.Response findById(Long id){
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("게시글이 존재하지 않습니다."));
        return new PostDto.Response(post);
    }

    @Transactional
    public void update(PostDto.Request dto, Long id){
        Post post = postRepository.findById(id).get();
        post.update(dto.getTitle(), dto.getContents());
    }

    @Transactional
    public void delete(Long id){
        Post post = postRepository.findById(id).get();
        postRepository.delete(post);
    }


    public List<PostDto.Response> findAll() {
        return postRepository.findAll().stream()
                .map(post -> new PostDto.Response(post)).toList();
    }
}