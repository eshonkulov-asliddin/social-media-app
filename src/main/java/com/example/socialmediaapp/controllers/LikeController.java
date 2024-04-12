package com.example.socialmediaapp.controllers;

import com.example.socialmediaapp.entities.Like;
import com.example.socialmediaapp.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/posts/{postId}/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping
    public ResponseEntity<Like> createLike(@PathVariable Long userId, @PathVariable Long postId) {
        Like like = likeService.createLike(userId, postId);
        return new ResponseEntity<>(like, HttpStatus.CREATED);
    }

    @DeleteMapping("/{likeId}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long likeId) {
        likeService.deleteLike(likeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}