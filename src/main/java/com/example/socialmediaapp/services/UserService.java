package com.example.socialmediaapp.services;

import com.example.socialmediaapp.entities.User;
import com.example.socialmediaapp.repositories.UserRepository;
import com.example.socialmediaapp.validations.UserValidator;
import com.example.socialmediaapp.validations.ValidModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new user in the system.
     *
     * @param user User details
     * @return The newly created user
     */
    public User createUser(@ValidModel(validatedBy = UserValidator.class, dtoGroup = User.class) User user) {
        return userRepository.save(user);
    }

    /**
     * Updates a user's details.
     *
     * @param id Id of the user to update
     * @param updatedUser New user details
     * @return The updated user
     */
    public User updateUser(Long id,
                           @ValidModel(validatedBy = UserValidator.class, dtoGroup = User.class) User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        return userRepository.save(existingUser);
    }

    /**
     * Fetches a user from the system.
     *
     * @param id Id of the user to fetch
     * @return The requested user
     */
    public User getUser(Long id) {
        // TODO: Handle the case when the user is not found
        return userRepository.findById(id)
                .orElse(null);
    }

    /**
     * Deletes a user from the system.
     *
     * @param id Id of the user to delete
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        // TODO: Handle any clean up operation required when deleting a user
        userRepository.deleteById(id);
    }
}