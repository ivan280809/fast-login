package com.readtrack.userservice.infrastructure.adapters.database;

import com.readtrack.userservice.domain.models.User;
import com.readtrack.userservice.domain.models.UserLogin;
import com.readtrack.userservice.domain.ports.out.UserLoginDatabasePort;
import com.readtrack.userservice.infrastructure.exceptions.LoginFindErrorException;
import com.readtrack.userservice.infrastructure.exceptions.LoginValidationErrorException;
import com.readtrack.userservice.infrastructure.mappers.UserMapper;
import com.readtrack.userservice.infrastructure.models.UserMO;
import com.readtrack.userservice.infrastructure.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserLoginDatabaseAdapter implements UserLoginDatabasePort {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  public User validateLogin(UserLogin userLogin) {
    UserMO userMO = findUserMO(userLogin);
    validatePassword(userLogin, userMO);
    return userMapper.toUser(userMO);
  }

  private UserMO findUserMO(UserLogin userLogin) {
    return userLogin.isEmailLogin()
        ? userRepository
            .findUserMOByEmail(userLogin.getUsernameOrEmail())
            .orElseThrow(() -> getLoginValidationErrorException("Mail Not Found: ", userLogin))
        : userRepository
            .findUserMOByUsername(userLogin.getUsernameOrEmail())
            .orElseThrow(() -> getLoginValidationErrorException("User Not Found: ", userLogin));
  }

  private LoginFindErrorException getLoginValidationErrorException(String x, UserLogin userLogin) {
    return new LoginFindErrorException(x + userLogin.getUsernameOrEmail());
  }

  private void validatePassword(UserLogin userLogin, UserMO userMO) {
    if (!passwordEncoder.matches(userLogin.getPassword(), userMO.getPassword())) {
      throw new LoginValidationErrorException(
          "Password not match: " + userLogin.getUsernameOrEmail());
    }
  }
}
