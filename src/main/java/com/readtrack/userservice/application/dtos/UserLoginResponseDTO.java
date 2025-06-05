package com.readtrack.userservice.application.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserLoginResponseDTO {
  private String token;
  private String username;
  private String role;
}
