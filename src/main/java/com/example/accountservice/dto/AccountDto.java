package com.example.accountservice.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

public class AccountDto {

    @NotNull(message = "ID не может быть null")
    private Long id;

    @NotNull(message = "User ID не может быть null")
    private Long userId;

    @NotNull(message = "Валюта не может быть нулевой")
    @Size(min = 3, max = 3, message = "Код валюты должен состоять из 3 символов.")
    private String currency;

    @NotNull(message = "Баланс не может быть нулевым")
    @Positive(message = "Баланс должен быть отрицатиельным")
    private BigDecimal balance;

    public AccountDto(@NotNull(message = "ID не может быть null") Long id, @NotNull(message = "User ID не может быть null") Long userId, @NotNull(message = "Валюта не может быть нулевой") @Size(min = 3, max = 3, message = "Код валюты должен состоять из 3 символов.") String currency, @NotNull(message = "Баланс не может быть нулевым")
    @Positive(message = "Баланс должен быть отрицатиельным") BigDecimal balance) {
        this.id = id;
        this.userId = userId;
        this.currency = currency;
        this.balance = balance;
    }

    public AccountDto() {

    }

    // Getters и Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto that = (AccountDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(balance, that.balance) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance, currency, userId);
    }
}
