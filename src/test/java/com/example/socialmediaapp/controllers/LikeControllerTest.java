package com.example.socialmediaapp.controllers;

import com.example.socialmediaapp.entities.Like;
import com.example.socialmediaapp.services.LikeService;
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
public class LikeControllerTest {

    @Mock
    private LikeService likeService;

    @InjectMocks
    private LikeController likeController;

    @Test
    public void givenValidLike_whenCreateLike_thenLikeIsCreated() {
        Like like = new Like();
        Long userId = 1L;
        Long postId = 1L;
        when(likeService.createLike(userId, postId)).thenReturn(like);

        ResponseEntity<Like> response = likeController.createLike(userId, postId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(like, response.getBody());
        verify(likeService, times(1)).createLike(userId, postId);
    }

    @Test
    public void givenExistingLike_whenDeleteLike_thenNoContentIsReturned() {
        Long likeId = 1L;
        doNothing().when(likeService).deleteLike(likeId);

        ResponseEntity<Void> response = likeController.deleteLike(likeId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(likeService, times(1)).deleteLike(likeId);
    }
}