package com.readtrack.userservice.domain.ports.out;

import com.readtrack.userservice.domain.models.User;
import com.readtrack.userservice.domain.models.UserLogin;

public interface UserLoginDatabasePort {
    User validateLogin(UserLogin userLogin);
}
