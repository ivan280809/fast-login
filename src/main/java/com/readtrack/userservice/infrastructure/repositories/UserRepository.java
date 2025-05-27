package com.readtrack.userservice.infrastructure.repositories;

import com.readtrack.userservice.infrastructure.models.UserMO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserMO, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
