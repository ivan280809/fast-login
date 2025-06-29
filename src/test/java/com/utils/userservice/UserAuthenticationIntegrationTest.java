package com.utils.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utils.userservice.dto.AuthCredentialsRequest;
import com.utils.userservice.model.User;
import com.utils.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test") // Ensure application-test.properties is used
class UserAuthenticationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll(); // Clean up database before each test
    }

    @Test
    void testRegisterAndLoginAndAccessProtectedEndpoint() throws Exception {
        // Step 1: Register a new user
        User newUser = new User("integrationuser", "password123", "USER", "integration@example.com");

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("integrationuser"))
                .andExpect(jsonPath("$.email").value("integration@example.com"));

        // Verify user is saved and password encoded
        User savedUser = userRepository.findByUsername("integrationuser").orElseThrow();
        assertNotNull(savedUser.getId());
        assertTrue(passwordEncoder.matches("password123", savedUser.getPassword()));

        // Step 2: Login with the new user
        AuthCredentialsRequest loginRequest = new AuthCredentialsRequest("integrationuser", "password123");

        MvcResult loginResult = mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue()))
                .andExpect(jsonPath("$.user.username", is("integrationuser")))
                .andReturn();

        String responseString = loginResult.getResponse().getContentAsString();
        String token = objectMapper.readTree(responseString).get("token").asText();
        Long userId = objectMapper.readTree(responseString).get("user").get("id").asLong();


        // Step 3: Access a protected endpoint (/api/users/id/{id}) using the obtained token
        mockMvc.perform(get("/api/users/id/" + userId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("integrationuser"))
                .andExpect(jsonPath("$.id").value(userId));

        // Step 4: Access another protected endpoint (/api/users/username/{username})
        mockMvc.perform(get("/api/users/username/integrationuser")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("integrationuser"))
                .andExpect(jsonPath("$.email").value("integration@example.com"));

        // Step 5: Try to access protected endpoint with invalid token (optional, good to have)
        mockMvc.perform(get("/api/users/id/" + userId)
                        .header("Authorization", "Bearer invalid" + token))
                .andExpect(status().isForbidden()); // Or 401 depending on exact exception handling for bad tokens

         // Step 6: Try to access non-existent user
        mockMvc.perform(get("/api/users/id/9999")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }


    @Test
    void testRegisterUser_UsernameAlreadyExists() throws Exception {
        // Register a user first
        User existingUser = new User("existinguser", "password123", "USER", "existing@example.com");
        userRepository.save(new User(existingUser.getUsername(), passwordEncoder.encode(existingUser.getPassword()), existingUser.getRole(), existingUser.getEmail()));

        // Try to register another user with the same username
        User newUserWithSameUsername = new User("existinguser", "newpassword", "USER", "newemail@example.com");

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserWithSameUsername)))
                .andExpect(status().isBadRequest()) // Expecting a 400 Bad Request
                .andExpect(jsonPath("$.message").value("Username already exists"));
    }

    @Test
    void testLogin_UserNotFound() throws Exception {
        AuthCredentialsRequest loginRequest = new AuthCredentialsRequest("nonexistentuser", "password123");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized()); // Or 401
    }

    @Test
    void testLogin_IncorrectPassword() throws Exception {
        // Register a user first
        User user = new User("testlogin", "correctpassword", "USER", "testlogin@example.com");
        userRepository.save(new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getRole(), user.getEmail()));

        AuthCredentialsRequest loginRequest = new AuthCredentialsRequest("testlogin", "incorrectpassword");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized()); // Or 401
    }
}
