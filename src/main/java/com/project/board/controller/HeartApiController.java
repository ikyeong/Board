package com.project.board.controller;

import com.project.board.domain.User;
import com.project.board.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class HeartApiController {

    private final HttpSession httpSession;
    private final HeartService heartService;

    @PostMapping("/api/heart/{postId}")
    public ResponseEntity create(@PathVariable Long postId){
        User loginUser = (User) httpSession.getAttribute("LOGIN_USER");
        return ResponseEntity.ok(heartService.save(postId, loginUser));
    }

    @DeleteMapping("/api/heart/{postId}")
    public ResponseEntity delete(@PathVariable Long postId){
        User loginUser = (User) httpSession.getAttribute("LOGIN_USER");
        return ResponseEntity.ok(heartService.delete(postId, loginUser));
    }
}
