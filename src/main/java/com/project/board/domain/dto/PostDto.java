package com.project.board.domain.dto;

import com.project.board.domain.Post;
import com.project.board.domain.User;
import lombok.*;

import java.time.LocalDate;

public class PostDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{

        private Long id;
        private User user;
        private String title;
        private String contents;

        public Post toEntity(){
            Post post = Post.builder()
                    .id(id)
                    .user(user)
                    .title(title)
                    .contents(contents)
                    .timeStamp(LocalDate.now())
                    .build();
            return post;
        }
    }

    @Data
    public static class Response{

        private Long id;
        private Long userId;
        private String writer;
        private String title;
        private String contents;
        private LocalDate timeStamp;

        public Response(Post post){
            this.id = post.getId();
            this.userId = post.getUser().getId();
            this.writer = post.getUser().getNickname();
            this.title = post.getTitle();
            this.contents = post.getContents();
            this.timeStamp = post.getTimeStamp();
        }
    }
}