package com.example.accountservice.mapper;

import com.example.accountservice.dto.TransactionDto;
import com.example.accountservice.model.Account;
import com.example.accountservice.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    // Преобразование Transaction в TransactionDTO
    public TransactionDTO toDto(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setAccountId(transaction.getAccount().getId());
        dto.setAmount(transaction.getAmount());
        dto.setCurrency(transaction.getCurrency());
        dto.setTransactionDate(transaction.getTransactionDate());
        return dto;
    }

    // Преобразование TransactionDTO в Transaction
    public Transaction toEntity(TransactionDTO dto, Account account) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(dto.getAmount());
        transaction.setCurrency(dto.getCurrency());
        transaction.setTransactionDate(dto.getTransactionDate());
        return transaction;
    }
}