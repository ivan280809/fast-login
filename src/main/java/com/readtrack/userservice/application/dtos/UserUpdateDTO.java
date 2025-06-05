package com.readtrack.userservice.application.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
    private String oldPassword;
    private String newPassword;
}
