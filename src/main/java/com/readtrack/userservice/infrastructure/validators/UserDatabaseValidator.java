package com.readtrack.userservice.infrastructure.validators;

import com.readtrack.userservice.domain.exceptions.EmailAlreadyExistsException;
import com.readtrack.userservice.domain.exceptions.UsernameAlreadyExistsException;
import com.readtrack.userservice.domain.models.User;
import com.readtrack.userservice.infrastructure.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserDatabaseValidator {

  private final UserRepository userRepository;

  public void validateNewUser(User user) {
    if (userRepository.existsByUsername(user.getUsername())) {
      throw new UsernameAlreadyExistsException("User " + user.getUsername() + " already exists.");
    }
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new EmailAlreadyExistsException(
          "Email " + user.getEmail() + " is already associated with another account.");
    }
  }
}
