package com.readtrack.userservice.domain.ports.out;

import com.readtrack.userservice.domain.models.UserUpdate;

public interface UserUpdateDatabasePort {
  void updateUser(String username, UserUpdate userUpdate);
}
