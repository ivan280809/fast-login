package com.readtrack.userservice.application.mappers;

import com.readtrack.userservice.application.dtos.UserDTO;
import com.readtrack.userservice.domain.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserControllerMapperImpl implements UserControllerMapper {
    @Override
    public User mapUserDTOToUser(UserDTO userDTO) {
        return User.of(
                userDTO.getEmail(),
                userDTO.getUsername(),
                userDTO.getPassword(),
                "USER"
        );
    }

    @Override
    public UserDTO mapUserToUserDTO(User user) {
        return new UserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }
}
