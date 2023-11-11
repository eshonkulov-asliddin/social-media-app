package com.example.socialmediaapp.repositories;

import com.example.socialmediaapp.entities.Follow;
import com.example.socialmediaapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    /**
     * Finds a Follow by its associated User and Follower.
     *
     * @param user the User who is being followed
     * @param follower the User who is following
     * @return an Optional containing the Follow if it exists, or an empty Optional if it does not
     */
    @Query("SELECT f FROM Follow f WHERE f.user = :user AND f.follower = :follower")
    Optional<Follow> findByUserAndFollower(@Param("user") User user, @Param("follower") User follower);
}