package com.example.socialmediaapp.services;

import com.example.socialmediaapp.entities.Follow;
import com.example.socialmediaapp.entities.User;
import com.example.socialmediaapp.repositories.FollowRepository;
import com.example.socialmediaapp.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FollowServiceTest {

    @InjectMocks
    private FollowService followService;

    @Mock
    private FollowRepository followRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenValidUserAndFollower_whenFollowUser_thenNewFollowIsCreated() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(followRepository.findByUserAndFollower(any(User.class), any(User.class))).thenReturn(Optional.empty());
        when(followRepository.save(any(Follow.class))).thenAnswer(i -> i.getArguments()[0]);

        Follow follow = followService.followUser(1L, 1L);

        assertNotNull(follow);
    }

    @Test
    public void givenNonExistentUser_whenFollowUser_thenExceptionIsThrown() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> followService.followUser(1L, 1L));
    }

    @Test
    public void givenNonExistentFollower_whenFollowUser_thenExceptionIsThrown() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()), Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> followService.followUser(1L, 1L));
    }

    @Test
    public void givenExistingFollow_whenFollowUser_thenExceptionIsThrown() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(followRepository.findByUserAndFollower(any(User.class), any(User.class))).thenReturn(Optional.of(new Follow()));

        assertThrows(IllegalStateException.class, () -> followService.followUser(1L, 1L));
    }

    @Test
    public void givenExistingFollow_whenUnfollowUser_thenFollowIsDeleted() {
        when(followRepository.existsById(anyLong())).thenReturn(true);

        followService.unfollowUser(1L);

        verify(followRepository, times(1)).deleteById(1L);
    }

    @Test
    public void givenNonExistentFollow_whenUnfollowUser_thenExceptionIsThrown() {
        when(followRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> followService.unfollowUser(1L));
    }
}