package com.example.socialmediaapp.controllers;

import com.example.socialmediaapp.entities.User;
import com.example.socialmediaapp.services.UserService;
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
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void givenValidUser_whenCreateUser_thenUserIsCreated() {
        User user = new User();
        when(userService.createUser(user)).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).createUser(user);
    }

    @Test
    public void givenExistingUser_whenUpdateUser_thenUserIsUpdated() {
        User user = new User();
        Long id = 1L;
        when(userService.updateUser(id, user)).thenReturn(user);

        ResponseEntity<User> response = userController.updateUser(id, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).updateUser(id, user);
    }

    @Test
    public void givenExistingUser_whenGetUser_thenUserIsReturned() {
        User user = new User();
        Long id = 1L;
        when(userService.getUser(id)).thenReturn(user);

        ResponseEntity<User> response = userController.getUser(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).getUser(id);
    }

    @Test
    public void givenNonExistentUser_whenGetUser_thenNotFoundIsReturned() {
        Long id = 1L;
        when(userService.getUser(id)).thenReturn(null);

        ResponseEntity<User> response = userController.getUser(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).getUser(id);
    }

    @Test
    public void givenExistingUser_whenDeleteUser_thenNoContentIsReturned() {
        Long id = 1L;
        doNothing().when(userService).deleteUser(id);

        ResponseEntity<Void> response = userController.deleteUser(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(id);
    }
}