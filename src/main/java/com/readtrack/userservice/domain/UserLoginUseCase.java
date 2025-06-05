package com.readtrack.userservice.domain;

import com.readtrack.userservice.domain.models.User;
import com.readtrack.userservice.domain.models.UserLogin;
import com.readtrack.userservice.domain.models.UserTokenRole;
import com.readtrack.userservice.domain.ports.in.UserLoginPort;
import com.readtrack.userservice.domain.ports.out.UserLoginDatabasePort;
import com.readtrack.userservice.domain.ports.out.UserTokenPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserLoginUseCase implements UserLoginPort {

  private final UserLoginDatabasePort userLoginDatabasePort;
  private final UserTokenPort userTokenPort;

  @Override
  public UserTokenRole login(UserLogin userLogin) {
    User user = userLoginDatabasePort.validateLogin(userLogin);
    return userTokenPort.generateToken(user);
  }
}
