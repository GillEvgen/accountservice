package com.example.accountservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class TransactionDto {

    private Long id;
    private BigDecimal amount;
    private String currency;
    private Long accountId;
    private LocalDateTime transactionDate;

    public TransactionDto(Long id, BigDecimal amount, String currency, Long accountId, LocalDateTime transactionDate) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.accountId = accountId;
        this.transactionDate = transactionDate;
    }

    public TransactionDto() {

    }

    // Getters и Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDto that = (TransactionDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(accountId, that.accountId) &&
                Objects.equals(transactionDate, that.transactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, currency, accountId, transactionDate);
    }
}
