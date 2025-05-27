package com.readtrack.userservice.application.controllers;

import com.readtrack.userservice.application.dtos.UserDTO;
import com.readtrack.userservice.application.mappers.UserControllerMapper;
import com.readtrack.userservice.application.validators.UserRegisterValidator;
import com.readtrack.userservice.domain.models.User;
import com.readtrack.userservice.domain.ports.in.UserRegisterPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRegisterControllerTest {

    @Mock
    private UserRegisterValidator userRegisterValidator;

    @Mock
    private UserRegisterPort userRegisterPort;

    @Mock
    private UserControllerMapper userControllerMapper;

    @InjectMocks
    private UserRegisterController userRegisterController;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {
        userDTO = UserDTO.
                    builder()
                .username("testuser")
                .email("test@example.com")
                .password("Test1234!")
                .build();

        user = User.of("testuser", "test@example.com", "Test1234!", "USER");
    }

    @Test
    void registerUser_ShouldReturnCreatedStatus() {
        doNothing().when(userRegisterValidator).validate(userDTO);
        when(userControllerMapper.mapUserDTOToUser(userDTO)).thenReturn(user);
        doNothing().when(userRegisterPort).registerUser(user);

        ResponseEntity<Void> response = userRegisterController.registerUser(userDTO);

        assertEquals(201, response.getStatusCodeValue());
        verify(userRegisterValidator).validate(userDTO);
        verify(userControllerMapper).mapUserDTOToUser(userDTO);
        verify(userRegisterPort).registerUser(user);
    }
}
