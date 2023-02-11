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
import org.springframework.web.bind.annotation.RequestParam;

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
        dto = postService.updateView(dto);
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
        User loginUser = (User) session.getAttribute("LOGIN_USER");
        model.addAttribute("user",loginUser);
        model.addAttribute("post",postService.findById(id));
        return "post/post-update";
    }

    @GetMapping("post/search")
    public String searchPost(@RequestParam(name = "keyword") String keyword, Model model){
        User loginUser = (User) session.getAttribute("LOGIN_USER");
        if (keyword == null || keyword.equals("")){
            model.addAttribute("error_message", "검색어를 입력해주세요.");
            model.addAttribute("error_href","/");
            return "index";
        }
        List<PostDto.Response> search = postService.search(keyword);
        model.addAttribute("count",search.size());
        model.addAttribute("keyword",keyword);
        model.addAttribute("post",search);
        model.addAttribute("user",loginUser);
        return "post/post-search";
    }
}