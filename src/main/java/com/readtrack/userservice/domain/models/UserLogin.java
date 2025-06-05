package com.readtrack.userservice.domain.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserLogin {
  private String usernameOrEmail;
  private String password;
  private boolean emailLogin;
}
