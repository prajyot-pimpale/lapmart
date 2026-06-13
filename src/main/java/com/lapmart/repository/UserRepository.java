package com.lapmart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lapmart.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	// Spring generates: SELECT * FROM users WHERE email = ?
    // Optional → handles the case where user is not found (no NullPointerException)
    Optional<User> findByEmail(String email);

    // Spring generates: SELECT COUNT(*) > 0 FROM users WHERE email = ?
    // Used during registration to check duplicate email
    boolean existsByEmail(String email);
}
