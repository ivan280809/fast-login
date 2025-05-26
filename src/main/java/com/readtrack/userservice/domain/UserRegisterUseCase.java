package com.readtrack.userservice.domain;

import com.readtrack.userservice.domain.models.User;
import com.readtrack.userservice.domain.services.UserRegisterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRegisterUseCase {
    
    private final UserRegisterService userRegisterService;

    public void registerUser(String email, String username, String password) {
        User user = User.of(email, username, password, "USER");
        userRegisterService.registerUser(user);
    }
}