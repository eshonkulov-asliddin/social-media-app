package com.example.socialmediaapp.services;

import com.example.socialmediaapp.entities.User;
import com.example.socialmediaapp.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    private static final String USERNAME = "username";
    private static final String EMAIL = "email@test.com";
    private static final String PASSWORD = "password";
    private static final long EXISTING_ID = 1L;
    private static final long NON_EXISTING_ID = 42L;

    User existingUser = new User(USERNAME, EMAIL, PASSWORD);

    @Test
    public void givenValidUser_whenCreateUser_thenUserIsCreated() {

        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        assertDoesNotThrow(() ->
        {
            User returnedUser = userService.createUser(existingUser);
            assertAll(
                    () -> assertEquals(USERNAME, returnedUser.getUsername()),
                    () -> assertEquals(EMAIL, returnedUser.getEmail())
            );
        });

        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void givenExistingUser_whenUpdateUser_thenUserIsUpdated() {

        User updatedUser = new User("updatedUsername", "updatedEmail@test.com", "updatedPassword");
        when(userRepository.findById(EXISTING_ID)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        assertDoesNotThrow(() ->
        {
            User returnedUser = userService.updateUser(EXISTING_ID, updatedUser);
            assertAll(
                    () -> assertEquals(updatedUser.getUsername(), returnedUser.getUsername()),
                    () -> assertEquals(updatedUser.getEmail(), returnedUser.getEmail())
            );
        });

        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void givenNonExistentUser_whenUpdateUser_thenExceptionIsThrown(){
        when(userRepository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.updateUser(NON_EXISTING_ID, existingUser));
    }

    @Test
    public void givenExistingUser_whenGetUser_thenUserIsReturned() {

        when(userRepository.findById(EXISTING_ID)).thenReturn(Optional.of(existingUser));

        assertDoesNotThrow(() ->
        {
            User returnedUser = userService.getUser(EXISTING_ID);
            assertAll(
                    () -> assertEquals(existingUser.getUsername(), returnedUser.getUsername()),
                    () -> assertEquals(existingUser.getEmail(), returnedUser.getEmail())
            );
        });

        verify(userRepository, times(1)).findById(EXISTING_ID);
    }

    @Test
    public void givenNonExistentUser_whenGetUser_thenNullIsReturned() {

        when(userRepository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        assertNull(userService.getUser(NON_EXISTING_ID));

        verify(userRepository, times(1)).findById(NON_EXISTING_ID);
    }

    @Test
    public void givenExistingUser_whenDeleteUser_thenUserIsDeleted() {

        when(userRepository.existsById(EXISTING_ID)).thenReturn(true);

        assertDoesNotThrow(() -> userService.deleteUser(EXISTING_ID));

        verify(userRepository, times(1)).existsById(EXISTING_ID);
        verify(userRepository, times(1)).deleteById(EXISTING_ID);
    }

    @Test
    public void givenNonExistentUser_whenDeleteUser_thenExceptionIsThrown() {

        when(userRepository.existsById(NON_EXISTING_ID)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.deleteUser(NON_EXISTING_ID));

        verify(userRepository, times(1)).existsById(NON_EXISTING_ID);
        verify(userRepository, times(0)).deleteById(anyLong());
    }
}