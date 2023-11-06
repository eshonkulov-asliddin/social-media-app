package com.example.socialmediaapp.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    private String email;

    @OneToMany(mappedBy="author")
    private Set<Post> posts;

    @OneToMany(mappedBy="user")
    private Set<Follow> follows;

    @OneToMany(mappedBy="follower")
    private Set<Follow> followedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Follow> getFollows() {
        return follows;
    }

    public void setFollows(Set<Follow> follows) {
        this.follows = follows;
    }

    public Set<Follow> getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(Set<Follow> followedBy) {
        this.followedBy = followedBy;
    }
}