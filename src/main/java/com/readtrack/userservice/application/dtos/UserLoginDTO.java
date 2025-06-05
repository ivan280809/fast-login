package com.readtrack.userservice.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginDTO {
  private String usernameOrEmail;
  private String password;
}
