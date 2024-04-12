package com.example.socialmediaapp.services;

import com.example.socialmediaapp.entities.Like;
import com.example.socialmediaapp.entities.Post;
import com.example.socialmediaapp.entities.User;
import com.example.socialmediaapp.repositories.LikeRepository;
import com.example.socialmediaapp.repositories.PostRepository;
import com.example.socialmediaapp.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LikeServiceTest {

    @InjectMocks
    private LikeService likeService;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenValidUserAndPost_whenCreateLike_thenShouldCreateLike() {
        User user = new User();
        Post post = new Post();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(likeRepository.findByUserAndPost(user, post)).thenReturn(Optional.empty());
        when(likeRepository.save(any(Like.class))).thenAnswer(i -> i.getArguments()[0]);

        Like like = likeService.createLike(1L, 1L);

        assertNotNull(like);
        assertEquals(user, like.getUser());
        assertEquals(post, like.getPost());
    }

    @Test
    public void givenUserNotFound_whenCreateLike_thenShouldThrowException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> likeService.createLike(1L, 1L));
    }

    @Test
    public void givenPostNotFound_whenCreateLike_thenShouldThrowException() {
        User user = new User();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> likeService.createLike(1L, 1L));
    }

    @Test
    public void givenLikeAlreadyExists_whenCreateLike_thenShouldThrowException() {
        User user = new User();
        Post post = new Post();
        Like like = new Like(user, post);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(likeRepository.findByUserAndPost(user, post)).thenReturn(Optional.of(like));

        assertThrows(IllegalStateException.class, () -> likeService.createLike(1L, 1L));
    }

    @Test
    public void givenValidLike_whenDeleteLike_thenShouldDeleteLike() {
        when(likeRepository.existsById(anyLong())).thenReturn(true);

        likeService.deleteLike(1L);

        verify(likeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void givenLikeNotFound_whenDeleteLike_thenShouldThrowException() {
        when(likeRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> likeService.deleteLike(1L));
    }
}
