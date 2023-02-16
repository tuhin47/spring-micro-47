package com.microservice.authservice.repository;

import com.microservice.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsernameOrEmail(String username,String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User findById(int userId);
}
