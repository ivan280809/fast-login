package com.readtrack.userservice.infrastructure.mappers.impl;

import com.readtrack.userservice.domain.models.User;
import com.readtrack.userservice.infrastructure.mappers.UserMapper;
import com.readtrack.userservice.infrastructure.models.UserMO;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserMO toUserMO(User user) {
            return UserMO.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .role(user.getRole())
                    .build();
        }

    @Override
    public User toUser(UserMO userMO) {
        return User.builder()
                .id(userMO.getId())
                .username(userMO.getUsername())
                .email(userMO.getEmail())
                .password(userMO.getPassword())
                .role(userMO.getRole())
                .build();

    }
}
