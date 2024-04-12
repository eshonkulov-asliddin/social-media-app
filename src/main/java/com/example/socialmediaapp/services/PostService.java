package com.example.socialmediaapp.services;

import com.example.socialmediaapp.entities.Post;
import com.example.socialmediaapp.entities.User;
import com.example.socialmediaapp.repositories.PostRepository;
import com.example.socialmediaapp.repositories.UserRepository;
import com.example.socialmediaapp.validations.PostValidator;
import com.example.socialmediaapp.validations.ValidModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new post in the system.
     *
     * @param userId The ID of the user who is creating the post
     * @param post   The details of the post to be created
     * @return The created post
     */
    public Post createPost(Long userId,
                           @ValidModel(validatedBy = PostValidator.class, dtoGroup = Post.class) Post post) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        // Set the user as the author of the post
        post.setAuthor(user);
        return postRepository.save(post);
    }

    /**
     * Updates the details of an existing post.
     *
     * @param postId   The ID of the post to update
     * @param newPost The new details of the post
     * @return The updated post
     */

    public Post updatePost(Long postId,
                           @ValidModel(validatedBy = PostValidator.class, dtoGroup = Post.class) Post newPost) {
        return postRepository.findById(postId)
                .map(post -> {
                    post.setTitle(newPost.getTitle());
                    post.setBody(newPost.getBody());
                    return postRepository.save(post);
                })
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    /**
     * Deletes a post from the system.
     *
     * @param postId The ID of the post to delete
     */
    public void deletePost(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.deleteById(postId);
    }

    /**
     * Fetches a post from the system.
     *
     * @param postId The ID of the post to fetch
     * @return The fetched post
     */
    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    /**
     * Fetches all posts from the system.
     *
     * @return The list of all posts
     */
    public Iterable<Post> getPosts() {
        return postRepository.findAll();
    }
}
