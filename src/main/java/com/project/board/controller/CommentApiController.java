package com.project.board.controller;

import com.project.board.domain.User;
import com.project.board.domain.dto.CommentDto;
import com.project.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final HttpSession httpSession;

    @PostMapping("/api/post/{id}/comment")
    public ResponseEntity create(@RequestBody CommentDto.Request dto, @PathVariable Long id){
        return ResponseEntity.ok(commentService.save(dto, id, (User) httpSession.getAttribute("LOGIN_USER")));
    }

    @GetMapping("/api/post/{id}/comment")
    public ResponseEntity read(@PathVariable Long id){
        return ResponseEntity.ok(commentService.findAll(id));
    }

    @PutMapping("/api/post/{postId}/comment/{commentId}")
    public ResponseEntity update(@RequestBody CommentDto.Request dto,
                                 @PathVariable( name = "commentId") Long id){
        commentService.update(dto,id);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/api/post/{postId}/comment/{commentId}")
    public ResponseEntity delete(@PathVariable(name = "commentId") Long id){
        commentService.delete(id);
        return ResponseEntity.ok(id);
    }
}
