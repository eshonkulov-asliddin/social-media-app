package com.example.socialmediaapp.entities;

import jakarta.persistence.*;

@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="follower_id", nullable=false)
    private User follower;

    public Follow(Long id, User user, User follower) {
        this.id = id;
        this.user = user;
        this.follower = follower;
    }

    public Follow(User user, User follower) {
        this.user = user;
        this.follower = follower;
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

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }
}