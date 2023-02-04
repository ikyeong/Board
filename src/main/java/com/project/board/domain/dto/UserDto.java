package com.project.board.domain.dto;

import com.project.board.domain.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{

        private Long id;
        @NotBlank( message = "아이디는 비워둘 수 없습니다.")
        @Pattern( regexp = ".{4,20}", message = "아이디는 4~20자리여야 합니다.")
        private String username;
        @NotBlank( message = "비밀번호는 비워둘 수 없습니다.")
        @Pattern( regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,20}", message = "비밀번호는 8~20자 영문자, 숫자를 포함해야 합니다.")
        private String password;
        @NotBlank( message = "닉네임은 비워둘 수 없습니다.")
        @Pattern( regexp = ".{2,10}", message = "닉네임은 2~10자리여야 합니다.")
        private String nickname;

        public User toEntity(){
            User user = User.builder()
                    .id(id)
                    .username(username)
                    .password(password)
                    .nickname(nickname)
                    .build();
            return user;
        }
    }

    @Data
    public static class Response{
        private Long id;
        private String username;
        private String password;
        private String nickname;

        public Response(User user){
            this.id = user.getId();
            this.username = user.getUsername();
            this.password = user.getPassword();
            this.nickname = user.getNickname();
        }
    }

}
