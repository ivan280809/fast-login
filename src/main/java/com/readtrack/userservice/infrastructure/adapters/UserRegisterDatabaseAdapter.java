package com.readtrack.userservice.infrastructure.adapters;

import com.readtrack.userservice.domain.models.User;
import com.readtrack.userservice.domain.ports.out.UserRegisterDatabasePort;
import com.readtrack.userservice.infrastructure.mappers.UserMapper;
import com.readtrack.userservice.infrastructure.repositories.UserRepository;
import com.readtrack.userservice.infrastructure.validators.UserDatabaseValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserRegisterDatabaseAdapter implements UserRegisterDatabasePort {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserDatabaseValidator userDatabaseValidator;

    @Override
    @Transactional
    public void saveUser(User user) {
        userDatabaseValidator.validateNewUser(user);
        User userWithEncodedPassWord = encodeUserPassword(user);
        userRepository.save(userMapper.toUserMO(userWithEncodedPassWord));
    }

    private User encodeUserPassword(User user) {
        String encodePassword = passwordEncoder.encode(user.getPassword());
        return User.of(user.getEmail() ,user.getUsername(), encodePassword, user.getRole());
    }
}
