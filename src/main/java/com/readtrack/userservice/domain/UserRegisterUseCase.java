package com.readtrack.userservice.domain;

import com.readtrack.userservice.domain.models.User;
import com.readtrack.userservice.domain.ports.in.UserRegisterPort;
import com.readtrack.userservice.domain.services.UserRegisterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRegisterUseCase implements UserRegisterPort {
    
    private final UserRegisterService userRegisterService;

    @Override
    public void registerUser(User user) {
        userRegisterService.registerUser(user);
    }
}