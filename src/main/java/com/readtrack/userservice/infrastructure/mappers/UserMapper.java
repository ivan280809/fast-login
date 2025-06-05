package com.readtrack.userservice.infrastructure.mappers;

import com.readtrack.userservice.domain.models.User;
import com.readtrack.userservice.infrastructure.models.UserMO;

public interface UserMapper {
  UserMO toUserMO(User user);

  User toUser(UserMO userMO);
}
