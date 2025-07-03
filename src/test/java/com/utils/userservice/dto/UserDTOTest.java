package com.utils.userservice.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    @Test
    void testUserDTOGettersAndSetters() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("testuser");
        userDTO.setRole("USER");
        userDTO.setEmail("test@example.com");

        assertEquals(1L, userDTO.getId());
        assertEquals("testuser", userDTO.getUsername());
        assertEquals("USER", userDTO.getRole());
        assertEquals("test@example.com", userDTO.getEmail());
    }

    @Test
    void testUserDTOAllArgsConstructor() {
        UserDTO userDTO = new UserDTO(1L, "testuser", "USER", "test@example.com");
        assertEquals(1L, userDTO.getId());
        assertEquals("testuser", userDTO.getUsername());
        assertEquals("USER", userDTO.getRole());
        assertEquals("test@example.com", userDTO.getEmail());
    }

    @Test
    void testUserDTONoArgsConstructor() {
        UserDTO userDTO = new UserDTO();
        assertNull(userDTO.getId());
        assertNull(userDTO.getUsername());
        assertNull(userDTO.getRole());
        assertNull(userDTO.getEmail());
    }
}
