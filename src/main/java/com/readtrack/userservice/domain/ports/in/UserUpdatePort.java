package com.readtrack.userservice.domain.ports.in;

import com.readtrack.userservice.domain.models.UserUpdate;

public interface UserUpdatePort {
    void updateUser(String username, UserUpdate userUpdate);
}
