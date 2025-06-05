package com.readtrack.userservice.application.mappers.impl;

import com.readtrack.userservice.application.dtos.UserLoginDTO;
import com.readtrack.userservice.application.dtos.UserLoginResponseDTO;
import com.readtrack.userservice.application.dtos.UserRegisterDTO;
import com.readtrack.userservice.application.dtos.UserUpdateDTO;
import com.readtrack.userservice.application.mappers.UserControllerMapper;
import com.readtrack.userservice.domain.models.User;
import com.readtrack.userservice.domain.models.UserLogin;
import com.readtrack.userservice.domain.models.UserTokenRole;
import com.readtrack.userservice.domain.models.UserUpdate;
import org.springframework.stereotype.Component;

@Component
public class UserControllerMapperImpl implements UserControllerMapper {
    @Override
    public User mapUserRegisterDTOToUser(UserRegisterDTO userRegisterDTO) {
        return User.of(
                userRegisterDTO.getEmail(),
                userRegisterDTO.getUsername(),
                userRegisterDTO.getPassword(),
                "USER"
        );
    }

    @Override
    public UserLogin mapUserLoginDTOToUser(UserLoginDTO userLoginDTO) {
        String usernameOrEmail = userLoginDTO.getUsernameOrEmail();
        return UserLogin.builder()
                .usernameOrEmail(usernameOrEmail)
                .password(userLoginDTO.getPassword())
                .emailLogin(isEmailLogin(usernameOrEmail))
                .build();
    }

    @Override
    public UserLoginResponseDTO mapUserTokenRoleToUserLoginResponseDTO(UserTokenRole userTokenRole) {
        return UserLoginResponseDTO.builder()
                .token(userTokenRole.getToken())
                .username(userTokenRole.getUsername())
                .role(userTokenRole.getRole())
                .build();
    }

    @Override
    public UserUpdate mapUserUpdateDTOToUserUpdate(UserUpdateDTO userUpdateDTO) {
        return UserUpdate.builder()
                .oldPassword(userUpdateDTO.getOldPassword())
                .newPassword(userUpdateDTO.getNewPassword())
                .build();
    }

    private Boolean isEmailLogin(String usernameOrEmail) {
        return usernameOrEmail.contains("@");
    }
}
