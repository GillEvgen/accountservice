package com.example.accountservice.service;

import com.example.accountservice.dto.AccountDto;
import com.example.accountservice.exception.UserNotFoundException;
import com.example.accountservice.mapper.AccountMapper;
import com.example.accountservice.model.Account;
import com.example.accountservice.model.User;
import com.example.accountservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;


@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserService userService, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.accountMapper = accountMapper;
    }

    // Чтение данных, транзакция с режимом только для чтения
    @Transactional(readOnly = true)
    public Page<AccountDto> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable)
                .map(accountMapper::toDto); // Преобразование в Dto
    }


    @Transactional(readOnly = true)
    public AccountDto getAccountById(Long accountId) throws AccountNotFoundException {
        return accountRepository.findById(accountId)
                .map(accountMapper::toDto)  // Преобразование в Dto
                .orElseThrow(() -> new AccountNotFoundException("Account with id " + accountId + " not found"));
    }

    // Создание нового аккаунта
    @Transactional
    public AccountDto createAccount(AccountDto accountDto) {
        // Получаем пользователя по ID
        User user = (User) userService.getUserById(accountDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with id " + accountDto.getUserId() + " not found"));

        // Преобразуем DTO в сущность Account
        Account account = accountMapper.toEntity(accountDto, user);
        account = accountRepository.save(account);  // Сохраняем новый аккаунт

        return accountMapper.toDto(account);  // Преобразуем обратно в Dto
    }

    // Пополнение баланса аккаунта
    @Transactional
    public AccountDto deposit(Long accountId, BigDecimal amount) throws AccountNotFoundException {
        // Находим аккаунт
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account with id " + accountId + " not found"));

        // Обновляем баланс
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);  // Сохраняем изменения

        return accountMapper.toDto(account);  // Преобразуем обратно в Dto
    }

    // Удаление аккаунта
    @Transactional
    public void deleteAccount(Long accountId) throws AccountNotFoundException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account with id " + accountId + " not found"));

        accountRepository.delete(account);
    }

}