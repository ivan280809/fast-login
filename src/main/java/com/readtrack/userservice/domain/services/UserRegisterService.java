package com.readtrack.userservice.domain.services;

import com.readtrack.userservice.domain.models.User;
import com.readtrack.userservice.domain.ports.out.UserRegisterDatabasePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRegisterService {

  private final UserRegisterDatabasePort userRegisterDatabasePort;

  public void registerUser(User user) {
    userRegisterDatabasePort.saveUser(user);
  }
}
