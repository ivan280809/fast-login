package com.readtrack.userservice.application.controllers;

import com.readtrack.userservice.application.dtos.UserRegisterDTO;
import com.readtrack.userservice.application.mappers.UserControllerMapper;
import com.readtrack.userservice.application.validators.UserControllerValidator;
import com.readtrack.userservice.domain.models.User;
import com.readtrack.userservice.domain.ports.in.UserRegisterPort;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/register")
@AllArgsConstructor
public class UserRegisterController {

    private final UserControllerValidator userControllerValidator;
    private final UserRegisterPort userRegisterPort;
    private final UserControllerMapper userControllerMapper;

    @PostMapping
    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        userControllerValidator.validateUserRegister(userRegisterDTO);
        User user = userControllerMapper.mapUserRegisterDTOToUser(userRegisterDTO);
        userRegisterPort.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
