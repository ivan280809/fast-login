package com.readtrack.userservice.domain.ports.in;

import com.readtrack.userservice.domain.models.UserLogin;
import com.readtrack.userservice.domain.models.UserTokenRole;

public interface UserLoginPort {
    UserTokenRole login(UserLogin userLogin);
}
