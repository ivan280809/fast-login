package com.utils.userservice.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthCredentialsRequestTest {

    @Test
    void testAuthCredentialsRequestGettersAndSetters() {
        AuthCredentialsRequest request = new AuthCredentialsRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        assertEquals("testuser", request.getUsername());
        assertEquals("password", request.getPassword());
    }

    @Test
    void testAuthCredentialsRequestAllArgsConstructor() {
        AuthCredentialsRequest request = new AuthCredentialsRequest("testuser", "password");
        assertEquals("testuser", request.getUsername());
        assertEquals("password", request.getPassword());
    }

    @Test
    void testAuthCredentialsRequestNoArgsConstructor() {
        AuthCredentialsRequest request = new AuthCredentialsRequest();
        assertNull(request.getUsername());
        assertNull(request.getPassword());
    }
}
