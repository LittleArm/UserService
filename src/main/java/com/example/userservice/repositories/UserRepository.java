package com.example.userservice.repositories;

import com.example.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    void deleteUserById(Long id);

    Optional<User> findByEmail(String email);
}
