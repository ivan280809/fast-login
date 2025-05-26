package com.readtrack.userservice.domain.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @EqualsAndHashCode.Include
    private Long id;

    private String email;
    private String username;
    private String password;
    private String role;


    public static User of(String email, String username, String password, String role) {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email is required");
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Username is required");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("Password is required");
        return new User(null, email, username, password, role);
    }
}
