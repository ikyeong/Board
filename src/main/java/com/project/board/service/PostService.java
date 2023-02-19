package com.project.board.service;

import com.project.board.domain.Post;
import com.project.board.domain.User;
import com.project.board.domain.dto.PostDto;
import com.project.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostDto.Response save(PostDto.Request dto, User loginUser) {
        Post post = dto.toEntity();
        post.setUser(loginUser);
        post.setTimeStamp(LocalDate.now());
        post.setComments(new ArrayList<>());
        loginUser.addPost(post);
        return new PostDto.Response(postRepository.save(post));
    }

    @Transactional
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

    @Transactional
    public List<PostDto.Response> findAll() {
        return postRepository.findAllByOrderByIdDesc().stream()
                .map(post -> new PostDto.Response(post)).toList();
    }

    @Transactional
    public List<PostDto.Response> search(String keyword) {
        List<Post> posts = postRepository.findByTitleContainingByOrderByIdDesc(keyword);
        return posts.stream()
                .map(post -> new PostDto.Response(post)).toList();
    }

    @Transactional
    public PostDto.Response updateView(PostDto.Response dto){
        Post post = postRepository.findById(dto.getId()).get();
        post.addView();
        dto.setView(dto.getView()+1);
        return dto;
    }
}
