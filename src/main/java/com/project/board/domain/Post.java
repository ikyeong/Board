package com.project.board.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

    @Id @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String contents;

    private LocalDate timeStamp;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    public void addComment(Comment comment){
        this.comments.add(comment);
        comment.setPost(this);
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
