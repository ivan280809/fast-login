package com.utils.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utils.userservice.dto.AuthCredentialsRequest;
import com.utils.userservice.dto.UserDTO;
import com.utils.userservice.model.User;
import com.utils.userservice.service.UserService;
import com.utils.userservice.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Use @ContextConfiguration to load SecurityConfig which includes JwtRequestFilter
@ContextConfiguration(classes = {SecurityConfig.class, UserController.class, JwtRequestFilter.class})
@WebMvcTest(UserController.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserService userService; // This is our UserDetailsService

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;
    private UserDetails testUserDetails;

    @BeforeEach
    void setUp() {
        // Apply Spring Security configuration to MockMvc
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()) // This applies the security filters
                .build();

        testUser = new User("testuser", "password", "USER", "test@example.com");
        testUser.setId(1L);

        testUserDetails = org.springframework.security.core.userdetails.User
                .withUsername("testuser")
                .password("password") // Password here is not used for auth in mock, but for UserDetails consistency
                .authorities("ROLE_USER")
                .build();

        // Clear security context before each test
        SecurityContextHolder.clearContext();
    }

    @Test
    void testLogin_Success() throws Exception {
        AuthCredentialsRequest authRequest = new AuthCredentialsRequest("testuser", "password");
        org.springframework.security.core.Authentication authentication =
                new UsernamePasswordAuthenticationToken(testUserDetails, null, testUserDetails.getAuthorities());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtil.generateToken(testUserDetails)).thenReturn("mocked_jwt_token");
        when(userService.getUserByUsername(authRequest.getUsername())).thenReturn(Optional.of(testUser));

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked_jwt_token"))
                .andExpect(jsonPath("$.user.username").value("testuser"));
    }

    @Test
    void testRegisterUser_Success() throws Exception {
        User userToRegister = new User("newuser", "rawPassword", "USER", "new@example.com");
        User registeredUser = new User("newuser", "encodedPassword", "USER", "new@example.com");
        registeredUser.setId(2L);

        when(userService.save(any(User.class))).thenReturn(registeredUser);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userToRegister)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newuser"))
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.email").value("new@example.com"));
    }

    // Helper to mock JWT validation and UserDetailsService for secured endpoints
    private void mockAuthenticationForProtectedEndpoint() {
        when(jwtUtil.getUsernameFromToken("mocked_jwt_token")).thenReturn("testuser");
        when(userService.loadUserByUsername("testuser")).thenReturn(testUserDetails);
        when(jwtUtil.validateToken("mocked_jwt_token", testUserDetails)).thenReturn(true);

        // Simulate successful authentication by JwtRequestFilter
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(testUserDetails, null, testUserDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(null)); // Mock details
        SecurityContextHolder.getContext().setAuthentication(authentication); // Set in SecurityContext
    }


    @Test
    void testGetUserById_UserFound() throws Exception {
        // Mocks for JwtRequestFilter's successful authentication path
        when(jwtUtil.getUsernameFromToken("mocked_jwt_token")).thenReturn(testUserDetails.getUsername());
        when(userService.loadUserByUsername(testUserDetails.getUsername())).thenReturn(testUserDetails);
        when(jwtUtil.validateToken("mocked_jwt_token", testUserDetails)).thenReturn(true);

        when(userService.getUserById(1L)).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/api/users/id/1")
                        .header("Authorization", "Bearer mocked_jwt_token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void testGetUserById_UserNotFound() throws Exception {
        when(jwtUtil.getUsernameFromToken("mocked_jwt_token")).thenReturn(testUserDetails.getUsername());
        when(userService.loadUserByUsername(testUserDetails.getUsername())).thenReturn(testUserDetails);
        when(jwtUtil.validateToken("mocked_jwt_token", testUserDetails)).thenReturn(true);

        when(userService.getUserById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/id/2")
                        .header("Authorization", "Bearer mocked_jwt_token"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetUserByUsername_UserFound() throws Exception {
        when(jwtUtil.getUsernameFromToken("mocked_jwt_token")).thenReturn(testUserDetails.getUsername());
        when(userService.loadUserByUsername(testUserDetails.getUsername())).thenReturn(testUserDetails);
        when(jwtUtil.validateToken("mocked_jwt_token", testUserDetails)).thenReturn(true);

        when(userService.getUserByUsername("testuser")).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/api/users/username/testuser")
                        .header("Authorization", "Bearer mocked_jwt_token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void testGetUserByUsername_UserNotFound() throws Exception {
        when(jwtUtil.getUsernameFromToken("mocked_jwt_token")).thenReturn(testUserDetails.getUsername());
        when(userService.loadUserByUsername(testUserDetails.getUsername())).thenReturn(testUserDetails);
        when(jwtUtil.validateToken("mocked_jwt_token", testUserDetails)).thenReturn(true);

        when(userService.getUserByUsername("unknownuser")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/username/unknownuser")
                        .header("Authorization", "Bearer mocked_jwt_token"))
                .andExpect(status().isNotFound());
    }
}
