package com.readtrack.userservice.application.mappers;

import com.readtrack.userservice.application.dtos.UserRegisterDTO;
import com.readtrack.userservice.domain.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserControllerMapperImpl implements UserControllerMapper {
    @Override
    public User mapUserDTOToUser(UserRegisterDTO userRegisterDTO) {
        return User.of(
                userRegisterDTO.getEmail(),
                userRegisterDTO.getUsername(),
                userRegisterDTO.getPassword(),
                "USER"
        );
    }

    @Override
    public UserRegisterDTO mapUserToUserDTO(User user) {
        return new UserRegisterDTO(
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }
}
