package com.example.socialmediaapp.repositories;

import com.example.socialmediaapp.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
