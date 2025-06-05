package com.readtrack.userservice.application.controllers;

import com.readtrack.userservice.application.dtos.UserLoginDTO;
import com.readtrack.userservice.application.dtos.UserLoginResponseDTO;
import com.readtrack.userservice.application.mappers.UserControllerMapper;
import com.readtrack.userservice.domain.models.UserLogin;
import com.readtrack.userservice.domain.models.UserTokenRole;
import com.readtrack.userservice.domain.ports.in.UserLoginPort;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class UserLoginController {

  private final UserLoginPort userLoginPort;
  private final UserControllerMapper userControllerMapper;

  @PostMapping
  public ResponseEntity<UserLoginResponseDTO> loginUser(
      @RequestBody @Valid UserLoginDTO userLoginDTO) {
    UserLogin userLogin = userControllerMapper.mapUserLoginDTOToUser(userLoginDTO);
    UserTokenRole login = userLoginPort.login(userLogin);
    UserLoginResponseDTO userLoginResponseDTO =
        userControllerMapper.mapUserTokenRoleToUserLoginResponseDTO(login);
    return ResponseEntity.ok(userLoginResponseDTO);
  }
}
