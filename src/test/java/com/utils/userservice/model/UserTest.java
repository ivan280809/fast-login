package com.utils.userservice.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserGettersAndSetters() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole("USER");
        user.setEmail("test@example.com");

        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("USER", user.getRole());
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void testUserConstructors() {
        User user1 = new User(1L, "testuser1", "pass1", "USER", "test1@example.com");
        assertEquals(1L, user1.getId());
        assertEquals("testuser1", user1.getUsername());
        assertEquals("pass1", user1.getPassword());
        assertEquals("USER", user1.getRole());
        assertEquals("test1@example.com", user1.getEmail());

        User user2 = new User("testuser2", "pass2", "ADMIN", "test2@example.com");
        assertEquals("testuser2", user2.getUsername());
        assertEquals("pass2", user2.getPassword());
        assertEquals("ADMIN", user2.getRole());
        assertEquals("test2@example.com", user2.getEmail());
    }

    @Test
    void testUserBuilder() {
        User user = User.builder()
                .id(1L)
                .username("testuser")
                .password("password")
                .role("USER")
                .email("test@example.com")
                .build();

        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("USER", user.getRole());
        assertEquals("test@example.com", user.getEmail());
    }
}
