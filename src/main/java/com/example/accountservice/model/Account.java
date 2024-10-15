package com.example.accountservice.model;

import com.example.accountservice.dto.AccountDto;
import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "accounts")
public class Account extends AccountDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @NotNull(message = "Баланс не может быть нулевым")
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(length = 3, nullable = false)
    @NotNull(message = "Валюта не может быть нулевой")
    @Size(min = 3, max = 3, message = "Код валюты должен состоять из 3 символов.")
    private String currency;


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();

    public Account(@NotNull(message = "ID не может быть null") Long id,
                   @NotNull(message = "User ID не может быть null") Long userId,
                   @NotNull(message = "Валюта не может быть нулевой")
                   @Size(min = 3, max = 3, message = "Код валюты должен состоять из 3 символов.") String currency,
                   @NotNull(message = "Баланс не может быть нулевым")
                   @Positive(message = "Баланс должен быть отрицатиельным") BigDecimal balance) {
        super(id, userId, currency, balance);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(user, account.user) &&
                Objects.equals(balance, account.balance) &&
                Objects.equals(currency, account.currency) &&
                Objects.equals(transactions, account.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, balance, currency, transactions);
    }
}