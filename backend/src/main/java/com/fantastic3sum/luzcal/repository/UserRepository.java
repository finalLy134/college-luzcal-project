package com.fantastic3sum.luzcal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fantastic3sum.luzcal.entity.User;

public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByGoogleId(String googleId);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}