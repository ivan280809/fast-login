package com.readtrack.userservice.application.mappers;

import com.readtrack.userservice.application.dtos.UserLoginDTO;
import com.readtrack.userservice.application.dtos.UserLoginResponseDTO;
import com.readtrack.userservice.application.dtos.UserRegisterDTO;
import com.readtrack.userservice.domain.models.User;
import com.readtrack.userservice.domain.models.UserLogin;
import com.readtrack.userservice.domain.models.UserTokenRole;

public interface UserControllerMapper {

    User mapUserRegisterDTOToUser(UserRegisterDTO userRegisterDTO) ;

    UserLogin mapUserLoginDTOToUser(UserLoginDTO userLoginDTO);

    UserLoginResponseDTO mapUserTokenRoleToUserLoginResponseDTO(UserTokenRole userTokenRole);
}
