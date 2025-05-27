package com.readtrack.userservice.domain;

import com.readtrack.userservice.domain.ports.in.UserLoginPort;
import com.readtrack.userservice.domain.ports.out.UserLoginDatabasePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserLoginUseCase implements UserLoginPort {

    private final UserLoginDatabasePort userLoginDatabasePort;

    

}
