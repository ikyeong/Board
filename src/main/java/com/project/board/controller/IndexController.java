package com.project.board.controller;

import com.project.board.domain.User;
import com.project.board.domain.dto.CommentDto;
import com.project.board.domain.dto.PostDto;
import com.project.board.service.CommentService;
import com.project.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {
    
    private final PostService postService;
    private final CommentService commentService;
    private final HttpSession session;

    @GetMapping("/")
    public String index(Model model){
        User loginUser = (User) session.getAttribute("LOGIN_USER");
        List<PostDto.Response> all = postService.findAll();
        model.addAttribute("post",all);
        model.addAttribute("user",loginUser);
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "user/user-login";
    }

    @GetMapping("/join")
    public String join(){
        return "user/user-join";
    }

    @GetMapping("/logout")
    public String logout(){
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/post/write")
    public String writePost(Model model){
        User user = (User) session.getAttribute("LOGIN_USER");
        if (user == null){
            model.addAttribute("error_message", "로그인이 필요한 서비스입니다.");
            model.addAttribute("error_href","/login");
            return "index";
        }
        model.addAttribute("user",user);
        return "post/post-write";
    }

    @GetMapping("/post/read/{id}")
    public String readPost(@PathVariable Long id, Model model){
        User loginUser = (User) session.getAttribute("LOGIN_USER");
        PostDto.Response dto = postService.findById(id);
        if (loginUser != null && loginUser.getId().equals(dto.getUserId())){
            model.addAttribute("writer",true);
        }
        model.addAttribute("user",loginUser);
        model.addAttribute("post",dto);
        List<CommentDto.Response> comments = commentService.findAll(id);
        comments.forEach(comment -> {
            if (loginUser != null && comment.getUserId().equals(loginUser.getId()))
                comment.setIsWriter(true);
        });
        model.addAttribute("comments",comments);
        return "post/post-read";
    }

    @GetMapping("/post/update/{id}")
    public String updatePost(@PathVariable Long id, Model model){
        model.addAttribute("post",postService.findById(id));
        return "post/post-update";
    }
} //북마크????도 나쁘지 않을듯
//heart
//heart{{#isHeart}}{{heart}}{{/isHeart}}
//                     ->이게 -fill임
