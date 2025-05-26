package com.readtrack.userservice.domain.ports.out;

import com.readtrack.userservice.domain.models.User;

public interface UserRegisterDatabasePort {
    void saveUser(User user);
}
