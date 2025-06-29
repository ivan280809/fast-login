package com.utils.userservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private String secret = "testsecretkeytestsecretkeytestsecretkeytestsecretkey"; // Must be long enough for HS256

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        // Manually set the secret key for testing, as @Value won't work here
        try {
            java.lang.reflect.Field secretField = JwtUtil.class.getDeclaredField("secret");
            secretField.setAccessible(true);
            secretField.set(jwtUtil, secret);

            java.lang.reflect.Field expirationField = JwtUtil.class.getDeclaredField("expiration");
            expirationField.setAccessible(true);
            expirationField.set(jwtUtil, 3600000L); // 1 hour for testing
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private UserDetails createUserDetails(String username, String role) {
        return new User(username, "password", Collections.singletonList(new SimpleGrantedAuthority(role)));
    }

    @Test
    void testGenerateToken() {
        UserDetails userDetails = createUserDetails("testuser", "ROLE_USER");
        String token = jwtUtil.generateToken(userDetails);
        assertNotNull(token);
        assertEquals("testuser", jwtUtil.getUsernameFromToken(token));
    }

    @Test
    void testGetUsernameFromToken() {
        UserDetails userDetails = createUserDetails("testuser", "ROLE_USER");
        String token = jwtUtil.generateToken(userDetails);
        String username = jwtUtil.getUsernameFromToken(token);
        assertEquals("testuser", username);
    }

    @Test
    void testValidateToken_ValidToken() {
        UserDetails userDetails = createUserDetails("testuser", "ROLE_USER");
        String token = jwtUtil.generateToken(userDetails);
        assertTrue(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    void testValidateToken_InvalidUsername() {
        UserDetails userDetails1 = createUserDetails("testuser1", "ROLE_USER");
        UserDetails userDetails2 = createUserDetails("testuser2", "ROLE_USER");
        String token = jwtUtil.generateToken(userDetails1);
        assertFalse(jwtUtil.validateToken(token, userDetails2));
    }

    @Test
    void testValidateToken_ExpiredToken() throws InterruptedException {
        // Store original expiration to restore it later
        long originalExpiration = 0L;
        try {
            java.lang.reflect.Field expirationField = JwtUtil.class.getDeclaredField("expiration");
            expirationField.setAccessible(true);
            originalExpiration = (long) expirationField.get(jwtUtil);
            expirationField.set(jwtUtil, 1L); // 1 millisecond for expiration
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            fail("Failed to modify expiration time for testing: " + e.getMessage());
        }

        UserDetails userDetails = createUserDetails("testuser", "ROLE_USER");
        // Generate token with the very short expiration time
        String token = jwtUtil.generateToken(userDetails);

        // Wait for a short period to ensure the token expires
        Thread.sleep(100); // Wait 100ms, should be enough for a 1ms token to expire

        // Now, validate the token. It should be expired.
        final String finalToken = token; // Effectively final for lambda
        assertThrows(io.jsonwebtoken.ExpiredJwtException.class, () -> {
            jwtUtil.isTokenExpired(finalToken);
        }, "Token should be expired");

        // Also, validateToken should return false if the token is expired
        assertFalse(jwtUtil.validateToken(token, userDetails), "validateToken should return false for an expired token");

        // Restore original expiration time
        try {
            java.lang.reflect.Field expirationField = JwtUtil.class.getDeclaredField("expiration");
            expirationField.setAccessible(true);
            expirationField.set(jwtUtil, originalExpiration);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Log or handle, but don't fail the test if restoration fails, primary assertion is above
            System.err.println("Warning: Failed to restore original expiration time: " + e.getMessage());
        }
    }


    @Test
    void testGetClaimFromToken() {
        UserDetails userDetails = createUserDetails("testuser", "ROLE_USER");
        String token = jwtUtil.generateToken(userDetails);
        String subject = jwtUtil.getClaimFromToken(token, Claims::getSubject);
        assertEquals("testuser", subject);
    }

    @Test
    void testGetExpirationDateFromToken() {
        UserDetails userDetails = createUserDetails("testuser", "ROLE_USER");
        String token = jwtUtil.generateToken(userDetails);
        Date expirationDate = jwtUtil.getExpirationDateFromToken(token);
        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date()));
    }

    @Test
    void testIsTokenExpired_NotExpired() {
        UserDetails userDetails = createUserDetails("testuser", "ROLE_USER");
        String token = jwtUtil.generateToken(userDetails);
        assertFalse(jwtUtil.isTokenExpired(token));
    }


    @Test
    void testExtractAuthorities() {
        UserDetails userDetails = createUserDetails("testuser", "ROLE_ADMIN");
        String token = jwtUtil.generateToken(userDetails);
        Claims claims = jwtUtil.getAllClaimsFromToken(token); // Use the utility method
        // Authorities are stored as a comma-separated string in the "authorities" claim
        String authoritiesClaim = (String) claims.get("authorities");
        assertNotNull(authoritiesClaim, "Authorities claim should not be null");
        assertEquals("ROLE_ADMIN", authoritiesClaim);
    }
}
