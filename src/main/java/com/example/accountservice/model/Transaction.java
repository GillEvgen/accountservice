
package com.example.accountservice.model;

import com.example.accountservice.dto.TransactionDto;
import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction extends TransactionDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @NotNull(message = "Аккаунт не может быть пустым")
    private Account account;


    @Column(nullable = false)
    @Positive(message = "Сумма должна быть положительной")
    private BigDecimal amount;

    @Column(length = 3, nullable = false)
    @NotNull(message = "Валюта не может быть нулевой")
    @Size(min = 3, max = 3, message = "Код валюты должен состоять из 3 символов.")
    private String currency;

    @Column(nullable = false)
    @NotNull(message = "Дата транзакции не может быть нулевой.")
    private LocalDateTime transactionDate;

    TransactionType transactionType;

    public Transaction() {
    }

    public Transaction(Long id, Account account, BigDecimal amount, String currency, LocalDateTime transactionDate) {
        this.id = id;
        this.account = account;
        this.amount = amount;
        this.currency = currency;
        this.transactionDate = transactionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", account=" + account +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", transactionDate=" + transactionDate +
                ", transactionType=" + transactionType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(account, that.account) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(transactionDate, that.transactionDate) &&
                transactionType == that.transactionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, account, amount, currency, transactionDate, transactionType);
    }
}