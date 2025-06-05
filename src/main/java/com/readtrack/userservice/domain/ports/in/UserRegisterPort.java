package com.readtrack.userservice.domain.ports.in;

import com.readtrack.userservice.domain.models.User;

public interface UserRegisterPort {
  void registerUser(User user);
}
