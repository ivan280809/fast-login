package com.readtrack.userservice.infrastructure.repositories;

import com.readtrack.userservice.infrastructure.models.UserMO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserMO, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<UserMO> findUserMOByUsername(String username);

    Optional<UserMO> findUserMOByEmail(String email);
}
