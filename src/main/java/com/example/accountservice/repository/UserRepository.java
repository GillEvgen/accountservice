package com.example.accountservice.repository;

import com.example.accountservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByDocumentNumber(String documentNumber);
}
