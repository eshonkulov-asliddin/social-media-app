package com.example.socialmediaapp.repositories;

import com.example.socialmediaapp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}