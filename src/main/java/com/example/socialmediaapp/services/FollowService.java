package com.example.socialmediaapp.services;

import com.example.socialmediaapp.entities.Follow;
import com.example.socialmediaapp.entities.User;
import com.example.socialmediaapp.repositories.FollowRepository;
import com.example.socialmediaapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Autowired
    public FollowService(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new Follow for a given user and follower, effectively making the follower follow the user.
     *
     * @param userId the ID of the user who is being followed
     * @param followerId the ID of the user who is following
     * @return the created Follow
     * @throws IllegalArgumentException if the user or follower does not exist
     * @throws IllegalStateException if the follower is already following the user
     */
    public Follow followUser(Long userId, Long followerId) {
        // Fetch the user and follower from the database
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        User follower = userRepository.findById(followerId).orElseThrow(() -> new IllegalArgumentException("Follower not found"));

        // Check if the follower is already following the user
        Optional<Follow> existingFollow = followRepository.findByUserAndFollower(user, follower);
        if (existingFollow.isPresent()) {
            throw new IllegalStateException("Follower is already following this user");
        }

        // Create and save the new follow
        Follow follow = new Follow(user, follower);
        return followRepository.save(follow);
    }

    /**
     * Deletes a Follow by its ID, effectively making the follower unfollow the user.
     *
     * @param id the ID of the Follow to delete
     * @throws IllegalArgumentException if the Follow does not exist
     */
    public void unfollowUser(Long id) {
        // Check if the follow exists
        if (!followRepository.existsById(id)) {
            throw new IllegalArgumentException("Follow not found");
        }

        // Delete the follow
        followRepository.deleteById(id);
    }
}
