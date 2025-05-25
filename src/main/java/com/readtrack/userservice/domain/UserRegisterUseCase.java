package com.readtrack.userservice.domain;

import com.readtrack.userservice.domain.models.User;
import com.readtrack.userservice.domain.services.UserRegisterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRegisterUseCase {
    
    private final UserRegisterService userRegisterService;

    public void registerUser(String username, String password, String email) {
        User user = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .role("USER")
                .build();
    }
}