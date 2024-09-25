package com.example.accountservice.repository;

import com.example.accountservice.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Метод для поиска транзакций по ID аккаунта с поддержкой пагинации
    Page<Transaction> findByAccountId(Long accountId, Pageable pageable);
}
