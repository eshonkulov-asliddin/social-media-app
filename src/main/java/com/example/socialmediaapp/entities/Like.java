package com.example.socialmediaapp.entities;

import jakarta.persistence.*;

@Table(name = "LIKES")
@Entity
public class Like {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="post_id", nullable=false)
    private Post post;

    public Like(Long id, User user, Post post) {
        this.id = id;
        this.user = user;
        this.post = post;
    }

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    public Like() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
