package com.readtrack.userservice.application.mappers;

import com.readtrack.userservice.application.dtos.UserRegisterDTO;
import com.readtrack.userservice.domain.models.User;

public interface UserControllerMapper {

    User mapUserDTOToUser(UserRegisterDTO userRegisterDTO) ;

    UserRegisterDTO mapUserToUserDTO(User user);
}
