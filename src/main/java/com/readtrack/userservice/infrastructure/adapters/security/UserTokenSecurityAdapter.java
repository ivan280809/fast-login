package com.readtrack.userservice.infrastructure.adapters.security;

import com.readtrack.userservice.domain.models.User;
import com.readtrack.userservice.domain.models.UserTokenRole;
import com.readtrack.userservice.domain.ports.out.UserTokenPort;
import com.readtrack.userservice.infrastructure.config.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserTokenSecurityAdapter implements UserTokenPort {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public UserTokenRole generateToken(User user) {
    String username = user.getUsername();
    String role = user.getRole();
    String token = jwtTokenProvider.generateToken(username, role);
    return new UserTokenRole(token, username, role);
  }
}
