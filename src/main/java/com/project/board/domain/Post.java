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
    private LocalDate updateTimeStamp;
    private int view;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Heart> hearts;

    public void addComment(Comment comment){
        this.comments.add(comment);
        comment.setPost(this);
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
        this.updateTimeStamp = LocalDate.now();
    }

    public void addView(){
        this.view ++;
    }
}
