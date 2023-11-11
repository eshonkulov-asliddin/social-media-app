package com.example.socialmediaapp.services;

import com.example.socialmediaapp.entities.Post;
import com.example.socialmediaapp.entities.User;
import com.example.socialmediaapp.repositories.PostRepository;
import com.example.socialmediaapp.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    private static final Long USER_ID = 1L;
    private static final Long POST_ID = 1L;
    private static final Post POST = new Post();
    private static final User USER = new User();

    @BeforeEach
    void setUp() {
        POST.setId(POST_ID);
        POST.setTitle("Test Title");
        POST.setBody("Test Body");
        USER.setId(USER_ID);
    }

    @Test
    public void givenValidUserAndPost_whenCreatePost_thenPostIsCreated() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER));
        when(postRepository.save(any(Post.class))).thenReturn(POST);

        Post createdPost = postService.createPost(USER_ID, POST);

        assertAll(
                () -> assertNotNull(createdPost, "The saved post should not be null"),
                () -> assertEquals(POST.getTitle(), createdPost.getTitle(), "The post title should match"),
                () -> assertEquals(POST_ID, createdPost.getId(), "The post id should match")
        );

        verify(userRepository, times(1)).findById(USER_ID);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void givenExistingPost_whenUpdatePost_thenPostIsUpdated() {
        Post updatedPost = new Post();
        updatedPost.setTitle("Updated Title");
        updatedPost.setBody("Updated Body");
        when(postRepository.findById(POST_ID)).thenReturn(Optional.of(POST));
        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        Post postResponse = postService.updatePost(POST_ID, updatedPost);

        assertAll(
                () -> assertNotNull(postResponse, "The updated post should not be null"),
                () -> assertEquals(updatedPost.getTitle(), postResponse.getTitle(), "The post title should be updated"),
                () -> assertEquals(updatedPost.getBody(), postResponse.getBody(), "The post body should be updated")
        );

        verify(postRepository, times(1)).findById(POST_ID);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void givenExistingPost_whenDeletePost_thenPostIsDeleted() {
        when(postRepository.findById(POST_ID)).thenReturn(Optional.of(POST));

        postService.deletePost(POST_ID);

        verify(postRepository, times(1)).findById(POST_ID);
        verify(postRepository, times(1)).deleteById(POST_ID);
    }

    @Test
    public void givenExistingPost_whenGetPost_thenPostIsReturned() {
        when(postRepository.findById(POST_ID)).thenReturn(Optional.of(POST));

        Post obtainedPost = postService.getPost(POST_ID);

        assertAll(
                () -> assertNotNull(obtainedPost, "Obtained post should not be null"),
                () -> assertEquals(POST_ID, obtainedPost.getId(), "The post ids should match"),
                () -> assertEquals(POST.getTitle(), obtainedPost.getTitle(), "The post titles should match")
        );

        verify(postRepository, times(1)).findById(POST_ID);
    }

    // Here we assume non-existingId = 100L
    @Test
    public void givenNonExistentPost_whenGetPost_thenExceptionIsThrown(){
        assertThrows(RuntimeException.class, () -> postService.getPost(100L));
    }

    @Test
    public void givenNonExistentPost_whenDeletePost_thenExceptionIsThrown(){
        assertThrows(RuntimeException.class, () -> postService.deletePost(100L));
    }
}