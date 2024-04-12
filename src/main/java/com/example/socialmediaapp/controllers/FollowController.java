package com.example.socialmediaapp.controllers;

import com.example.socialmediaapp.entities.Follow;
import com.example.socialmediaapp.services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/followers")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/{followerId}")
    public ResponseEntity<Follow> followUser(@PathVariable Long userId, @PathVariable Long followerId) {
        Follow follow = followService.followUser(userId, followerId);
        return new ResponseEntity<>(follow, HttpStatus.CREATED);
    }

    @DeleteMapping("/{followId}")
    public ResponseEntity<Void> unfollowUser(@PathVariable Long followId) {
        followService.unfollowUser(followId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}