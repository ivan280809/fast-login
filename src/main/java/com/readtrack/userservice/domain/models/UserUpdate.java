package com.readtrack.userservice.domain.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserUpdate {
  private String oldPassword;
  private String newPassword;
}
