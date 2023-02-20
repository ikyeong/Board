package com.project.board.service;

import com.project.board.domain.Heart;
import com.project.board.domain.User;
import com.project.board.repository.HeartRepository;
import com.project.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final PostRepository postRepository;

    @Transactional
    public boolean exist(User user, Long postId){
        return heartRepository.findByUserAndPost(user,postRepository.findById(postId).get()).isPresent();
    }

    @Transactional
    public Heart save(Long postId, User user) {
        Heart heart = Heart.builder()
                .user(user)
                .post(postRepository.findById(postId).get())
                .build();
        heartRepository.save(heart);
        return heart;
    }

    @Transactional
    public Long delete(Long postId, User user) {
        Heart heart = heartRepository.findByUserAndPost(user, postRepository.findById(postId).get()).get();
        heartRepository.delete(heart);
        return postId;
    }
}
