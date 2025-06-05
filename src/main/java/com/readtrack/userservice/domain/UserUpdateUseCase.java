package com.readtrack.userservice.domain;

import com.readtrack.userservice.domain.models.UserUpdate;
import com.readtrack.userservice.domain.ports.in.UserUpdatePort;
import com.readtrack.userservice.domain.ports.out.UserUpdateDatabasePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserUpdateUseCase implements UserUpdatePort {

  private final UserUpdateDatabasePort userUpdateDatabasePort;

  @Override
  public void updateUser(String username, UserUpdate userUpdate) {
    userUpdateDatabasePort.updateUser(username, userUpdate);
  }
}
