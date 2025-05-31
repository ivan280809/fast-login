package com.readtrack.userservice.domain.ports.out;

import com.readtrack.userservice.domain.models.User;
import com.readtrack.userservice.domain.models.UserTokenRole;

public interface UserTokenPort {
    UserTokenRole generateToken(User user);
}
