package com.readtrack.userservice.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserTokenRole {
    private String token;
    private String username;
    private String role;
}
