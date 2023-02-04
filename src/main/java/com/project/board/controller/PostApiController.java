package com.project.board.controller;

import com.project.board.domain.User;
import com.project.board.domain.dto.PostDto;
import com.project.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final HttpSession session;

    @PostMapping("/api/post")
    @Transactional
    public ResponseEntity create(@RequestBody PostDto.Request dto){
        return ResponseEntity.ok(postService.save(dto, (User) session.getAttribute("LOGIN_USER")));
    }

    @GetMapping("/api/post/{id}")
    public ResponseEntity read(@PathVariable Long id){
        return ResponseEntity.ok(postService.findById(id));
    }

    @PutMapping("/api/post/{id}")
    public ResponseEntity update(@RequestBody PostDto.Request dto, @PathVariable Long id){
        postService.update(dto, id);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/api/post/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        postService.delete(id);
        return ResponseEntity.ok(id);
    }
}
