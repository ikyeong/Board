package com.project.board.controller;

import com.project.board.domain.User;
import com.project.board.domain.dto.UserDto;
import com.project.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/login")
    public String loginValidCheck(UserDto.Request dto, Model model, HttpServletRequest request){
        User loginUser = userService.loginValidationCheck(dto);
        if (loginUser == null){
            model.addAttribute("error","아이디 또는 비밀번호가 일치하지 않습니다.");
            return "user/user-login";
        }

        HttpSession session = request.getSession();
        session.setAttribute("LOGIN_USER", loginUser);
        return "redirect:/";
    }

    @PostMapping("/join")
    public String joinValidCheck(@Valid UserDto.Request dto, BindingResult bindingResult, Model model){
        if (userService.duplicationCheck(dto)){
            bindingResult.addError(new FieldError("UserDto.Request",
                                                    "username",
                                                    "존재하는 아이디입니다."));
        }
        if (bindingResult.hasErrors()){
            model.addAttribute("userDto",dto);
            Map<String, String> errorResult = userService.errorHandling(bindingResult);
            for (String key : errorResult.keySet()){
                model.addAttribute(key,errorResult.get(key));
            }
            return "user/user-join";
        }
        userService.save(dto);
        return "redirect:/";
    }
}
