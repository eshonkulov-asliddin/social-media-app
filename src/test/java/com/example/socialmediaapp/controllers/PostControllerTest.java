package com.example.socialmediaapp.controllers;

import com.example.socialmediaapp.entities.Post;
import com.example.socialmediaapp.services.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @Test
    public void givenValidPost_whenCreatePost_thenPostIsCreated() {
        Post post = new Post();
        Long userId = 1L;
        when(postService.createPost(userId, post)).thenReturn(post);

        ResponseEntity<Post> response = postController.createPost(userId, post);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(post, response.getBody());
        verify(postService, times(1)).createPost(userId, post);
    }

    @Test
    public void givenExistingPost_whenUpdatePost_thenPostIsUpdated() {
        Post post = new Post();
        Long postId = 1L;
        when(postService.updatePost(postId, post)).thenReturn(post);

        ResponseEntity<Post> response = postController.updatePost(postId, post);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(post, response.getBody());
        verify(postService, times(1)).updatePost(postId, post);
    }

    @Test
    public void givenExistingPost_whenGetPost_thenPostIsReturned() {
        Post post = new Post();
        Long postId = 1L;
        when(postService.getPost(postId)).thenReturn(post);

        ResponseEntity<Post> response = postController.getPost(postId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(post, response.getBody());
        verify(postService, times(1)).getPost(postId);
    }

    @Test
    public void whenGetPosts_thenPostsAreReturned() {
        Post post1 = new Post();
        Post post2 = new Post();
        List<Post> posts = Arrays.asList(post1, post2);
        when(postService.getPosts()).thenReturn(posts);

        ResponseEntity<Iterable<Post>> response = postController.getPosts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(posts, response.getBody());
        verify(postService, times(1)).getPosts();
    }

    @Test
    public void givenExistingPost_whenDeletePost_thenNoContentIsReturned() {
        Long postId = 1L;
        doNothing().when(postService).deletePost(postId);

        ResponseEntity<Void> response = postController.deletePost(postId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(postService, times(1)).deletePost(postId);
    }
}