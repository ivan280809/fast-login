package com.readtrack.userservice.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readtrack.userservice.application.dtos.UserRegisterDTO;
import com.readtrack.userservice.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserRegisterIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRegisterDTO baseUser;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        baseUser = new UserRegisterDTO("john_doe", "john@example.com", "Secure123!");
        userRepository.deleteAll();
    }


    @Test
    @WithMockUser
    void shouldRegisterUserSuccessfully() throws Exception {

        mockMvc.perform(post("/users/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(baseUser)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void shouldNotRegisterUserWithDuplicateUsername() throws Exception {
        mockMvc.perform(post("/users/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(baseUser)))
                .andExpect(status().isCreated());

        UserRegisterDTO duplicateUsername = new UserRegisterDTO("john_doe", "other@example.com", "Secure123!");
        mockMvc.perform(post("/users/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateUsername)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser
    void shouldNotRegisterUserWithDuplicateEmail() throws Exception {
        mockMvc.perform(post("/users/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(baseUser)))
                .andExpect(status().isCreated());

        UserRegisterDTO duplicateEmail = new UserRegisterDTO("another_user", "john@example.com", "Secure123!");
        mockMvc.perform(post("/users/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateEmail)))
                .andExpect(status().isConflict()); // Ajusta según tu lógica
    }

    @Test
    @WithMockUser
    void shouldNotRegisterUserWithInvalidPassword() throws Exception {
        UserRegisterDTO invalidPasswordUser = new UserRegisterDTO("weak_user", "weak@example.com", "123");

        mockMvc.perform(post("/users/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPasswordUser)))
                .andExpect(status().isBadRequest());
    }
}
