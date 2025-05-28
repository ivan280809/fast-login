package com.readtrack.userservice.domain.ports.out;

import com.readtrack.userservice.domain.models.User;
import com.readtrack.userservice.domain.models.UserLogin;
import com.readtrack.userservice.domain.models.UserTokenRole;

public interface UserLoginDatabasePort {

    User validateLogin(UserLogin userLogin);

    UserTokenRole generateToken(User user);
}
