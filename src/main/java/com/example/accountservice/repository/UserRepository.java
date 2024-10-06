package com.example.accountservice.repository;

import com.example.accountservice.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Метод для поиска всех пользователей с поддержкой пагинации
    Page<User> findAll(Pageable pageable);

    Optional<User> findByDocumentNumber(String documentNumber);
}
