package com.readtrack.userservice.application.mappers;

import com.readtrack.userservice.application.dtos.UserDTO;
import com.readtrack.userservice.domain.models.User;

public interface UserControllerMapper {

    User mapUserDTOToUser(UserDTO userDTO) ;

    UserDTO mapUserToUserDTO(User user);
}
