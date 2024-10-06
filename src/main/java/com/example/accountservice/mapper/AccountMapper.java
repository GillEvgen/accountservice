package com.example.accountservice.mapper;

import com.example.accountservice.dto.AccountDto;
import com.example.accountservice.model.Account;
import com.example.accountservice.model.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountMapper {

    // Преобразование Account в AccountDTO
    public AccountDto toDto(Account account) {
        AccountDto dto = new AccountDto(1L, 1L, "USD", new BigDecimal("100.00"));
        dto.setUserId(account.getUser().getId());
        dto.setCurrency(account.getCurrency());
        dto.setBalance(account.getBalance());
        return dto;
    }

    // Преобразование AccountDTO в Account (для создания аккаунта)
    public Account toEntity(AccountDto dto, User user) {
        Account account = new Account();
        account.setUser(user);
        account.setCurrency(dto.getCurrency());
        account.setBalance(dto.getBalance());
        return account;
    }
}