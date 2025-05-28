package com.readtrack.userservice.domain.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserTokenRole {
    private String token;
    private String username;
    private String role;
}
