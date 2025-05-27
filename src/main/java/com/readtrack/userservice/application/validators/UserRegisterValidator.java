package com.readtrack.userservice.application.validators;

import com.readtrack.userservice.application.dtos.UserRegisterDTO;
import com.readtrack.userservice.application.exceptions.InvalidPasswordException;
import com.readtrack.userservice.application.exceptions.InvalidUsernameException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserRegisterValidator {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
    );

    public void validate(UserRegisterDTO user) {
        if (!PASSWORD_PATTERN.matcher(user.getPassword()).matches()) {
            throw new InvalidPasswordException("Password must be at least 8 characters long, " +
                    "contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
        }

        if (user.getUsername().contains("@")) {
            throw new InvalidUsernameException("Username cannot contain '@' character.");
        }
    }
}
