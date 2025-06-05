package com.readtrack.userservice.application.controllers;

import com.readtrack.userservice.application.dtos.UserRegisterDTO;
import com.readtrack.userservice.application.mappers.UserControllerMapper;
import com.readtrack.userservice.application.validators.UserControllerValidator;
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
    private UserControllerValidator userControllerValidator;

    @Mock
    private UserRegisterPort userRegisterPort;

    @Mock
    private UserControllerMapper userControllerMapper;

    @InjectMocks
    private UserRegisterController userRegisterController;

    private UserRegisterDTO userRegisterDTO;
    private User user;

    @BeforeEach
    void setUp() {
        userRegisterDTO = UserRegisterDTO.
                    builder()
                .username("testuser")
                .email("test@example.com")
                .password("Test1234!")
                .build();

        user = User.of("testuser", "test@example.com", "Test1234!", "USER");
    }

    @Test
    void registerUser_ShouldReturnCreatedStatus() {
        doNothing().when(userControllerValidator).validateUserRegister(userRegisterDTO);
        when(userControllerMapper.mapUserRegisterDTOToUser(userRegisterDTO)).thenReturn(user);
        doNothing().when(userRegisterPort).registerUser(user);

        ResponseEntity<Void> response = userRegisterController.registerUser(userRegisterDTO);

        assertEquals(201, response.getStatusCodeValue());
        verify(userControllerValidator).validateUserRegister(userRegisterDTO);
        verify(userControllerMapper).mapUserRegisterDTOToUser(userRegisterDTO);
        verify(userRegisterPort).registerUser(user);
    }
}
