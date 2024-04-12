package com.example.socialmediaapp.controllers;

import com.example.socialmediaapp.entities.Follow;
import com.example.socialmediaapp.services.FollowService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FollowControllerTest {

    @Mock
    private FollowService followService;

    @InjectMocks
    private FollowController followController;

    @Test
    public void givenValidFollow_whenFollowUser_thenFollowIsCreated() {
        Follow follow = new Follow();
        Long userId = 1L;
        Long followerId = 1L;
        when(followService.followUser(userId, followerId)).thenReturn(follow);

        ResponseEntity<Follow> response = followController.followUser(userId, followerId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(follow, response.getBody());
        verify(followService, times(1)).followUser(userId, followerId);
    }

    @Test
    public void givenExistingFollow_whenUnfollowUser_thenNoContentIsReturned() {
        Long followId = 1L;
        doNothing().when(followService).unfollowUser(followId);

        ResponseEntity<Void> response = followController.unfollowUser(followId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(followService, times(1)).unfollowUser(followId);
    }
}