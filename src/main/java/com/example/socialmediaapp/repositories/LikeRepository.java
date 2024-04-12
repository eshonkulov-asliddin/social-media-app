package com.example.socialmediaapp.repositories;

import com.example.socialmediaapp.entities.Like;
import com.example.socialmediaapp.entities.Post;
import com.example.socialmediaapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    /**
     * Finds a Like by its associated User and Post.
     *
     * @param user the User who liked the Post
     * @param post the Post that was liked
     * @return an Optional containing the Like if it exists, or an empty Optional if it does not
     */
    @Query("SELECT l FROM Like l WHERE l.user = :user AND l.post = :post")
    Optional<Like> findByUserAndPost(@Param("user") User user, @Param("post") Post post);
}
