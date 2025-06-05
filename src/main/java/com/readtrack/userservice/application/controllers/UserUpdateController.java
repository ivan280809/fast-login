package com.readtrack.userservice.application.controllers;

import com.readtrack.userservice.application.dtos.UserUpdateDTO;
import com.readtrack.userservice.application.mappers.UserControllerMapper;
import com.readtrack.userservice.application.validators.UserControllerValidator;
import com.readtrack.userservice.domain.models.UserUpdate;
import com.readtrack.userservice.domain.ports.in.UserUpdatePort;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
@AllArgsConstructor
public class UserUpdateController {

  private final UserUpdatePort userUpdatePort;
  private final UserControllerMapper userControllerMapper;
  private final UserControllerValidator userControllerValidator;

  @PutMapping
  public ResponseEntity<Void> updateUser(
      @RequestBody @Valid UserUpdateDTO dto, Principal principal) {
    userControllerValidator.validatePassword(dto.getNewPassword());
    String username = principal.getName();
    UserUpdate userUpdate = userControllerMapper.mapUserUpdateDTOToUserUpdate(dto);
    userUpdatePort.updateUser(username, userUpdate);
    return ResponseEntity.noContent().build();
  }
}
