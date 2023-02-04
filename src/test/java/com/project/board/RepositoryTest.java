package com.project.board;

import com.project.board.domain.Post;
import com.project.board.domain.User;
import com.project.board.repository.PostRepository;
import com.project.board.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class RepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    @Test
    public void 유저등록(){
        User user = User.builder().username("ikyeong").password("1234").build();
        User saved = userRepository.save(user);

        Optional<User> result = userRepository.findByUsername("ikyeong");
        Assertions.assertThat(result.get().getPassword()).isEqualTo(saved.getPassword());
    }

    @Test
    public void 게시글등록(){
        User user = User.builder().username("ikyeong").password("1234").build();
        user.setPosts(new ArrayList<>());
        user = userRepository.save(user);
        System.out.println(user);
        Post post1 = Post.builder().title("title 1").contents("contents1").user(user).build();
        user.addPost(post1);
        Post post2 = Post.builder().title("title 2").contents("contents2").build();
        Post post3 = Post.builder().title("제목 3").user(user).contents("contents3").build();
        user.addPost(post3);
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        List<Post> postsByTitle = new ArrayList<>();
        List<Post> postsByUser = new ArrayList<>();

        postsByTitle.add(post1);
        postsByTitle.add(post2);
        postsByUser.add(post1);
        postsByUser.add(post3);

        List<Post> result1 = postRepository.findByTitleContaining("title");
        Assertions.assertThat(postsByTitle).isEqualTo(result1);
        List<Post> result2 = postRepository.findByUser(user);
        Assertions.assertThat(postsByUser).isEqualTo(result2);

        User resultUser = userRepository.findByUsername("ikyeong").get();
        Assertions.assertThat(postsByUser).isEqualTo(resultUser.getPosts());
    }
}