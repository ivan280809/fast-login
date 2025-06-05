package com.readtrack.userservice.infrastructure.adapters.database;

import com.readtrack.userservice.application.exceptions.InvalidPasswordException;
import com.readtrack.userservice.domain.models.UserUpdate;
import com.readtrack.userservice.domain.ports.out.UserUpdateDatabasePort;
import com.readtrack.userservice.infrastructure.exceptions.LoginFindErrorException;
import com.readtrack.userservice.infrastructure.models.UserMO;
import com.readtrack.userservice.infrastructure.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserUpdateDatabaseAdapter implements UserUpdateDatabasePort {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void updateUser(String username, UserUpdate userUpdate) {

    UserMO userMO =
        userRepository
            .findUserMOByUsername(username)
            .orElseThrow(() -> new LoginFindErrorException("User Not Found: " + username));

    validatePassword(username, userUpdate, userMO);

    userMO.setPassword(userUpdate.getNewPassword());

    userRepository.save(userMO);
  }

  private void validatePassword(String username, UserUpdate userUpdate, UserMO userMO) {
    if (!passwordEncoder.matches(userUpdate.getOldPassword(), userMO.getPassword())) {
      throw new InvalidPasswordException("Old password does not match for user: " + username);
    }
  }
}
