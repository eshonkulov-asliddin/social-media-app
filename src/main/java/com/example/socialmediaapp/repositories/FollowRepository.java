package com.example.socialmediaapp.repositories;

import com.example.socialmediaapp.entities.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
