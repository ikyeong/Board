package com.project.board.domain.dto;

import com.project.board.domain.Comment;
import com.project.board.domain.Post;
import com.project.board.domain.User;
import lombok.*;

import java.time.LocalDate;

public class CommentDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{

        private Long id;
        private User user;
        private Post post;
        private String contents;
        private LocalDate timeStamp;

        public Comment toEntity(){
            return Comment.builder()
                    .id(id)
                    .user(user)
                    .post(post)
                    .contents(contents)
                    .timeStamp(timeStamp)
                    .build();
        }
    }

    @Data
    public static class Response{
        private Long id;
        private Long userId;
        private String writer;
        private Long postId;
        private String contents;
        private LocalDate timeStamp;
        private Boolean isWriter;

        public Response(Comment comment){
            this.id = comment.getId();
            this.userId = comment.getUser().getId();
            this.writer = comment.getUser().getNickname();
            this.postId = comment.getPost().getId();
            this.contents = comment.getContents();
            this.timeStamp = comment.getTimeStamp();
            this.isWriter = false;
        }
    }
}
