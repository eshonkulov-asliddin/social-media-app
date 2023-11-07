package com.example.socialmediaapp.services;

import com.example.socialmediaapp.entities.Like;
import com.example.socialmediaapp.entities.Post;
import com.example.socialmediaapp.entities.User;
import com.example.socialmediaapp.repositories.LikeRepository;
import com.example.socialmediaapp.repositories.PostRepository;
import com.example.socialmediaapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository, UserRepository userRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    /**
     * Creates a new Like for a given user and post.
     *
     * @param userId the ID of the user who is liking the post
     * @param postId the ID of the post being liked
     * @return the created Like
     * @throws IllegalArgumentException if the user or post does not exist
     * @throws IllegalStateException if the user has already liked the post
     */
    public Like createLike(Long userId, Long postId) {
        // Fetch the user and post from the database
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // Check if the user has already liked the post
        Optional<Like> existingLike = likeRepository.findByUserAndPost(user, post);
        if (existingLike.isPresent()) {
            throw new IllegalStateException("User has already liked this post");
        }

        // Create and save the new like
        Like like = new Like(user, post);
        return likeRepository.save(like);
    }

    /**
     * Deletes a Like by its ID.
     *
     * @param id the ID of the Like to delete
     * @throws IllegalArgumentException if the Like does not exist
     */
    public void deleteLike(Long id) {
        // Check if the like exists
        if (!likeRepository.existsById(id)) {
            throw new IllegalArgumentException("Like not found");
        }

        // Delete the like
        likeRepository.deleteById(id);
    }
}