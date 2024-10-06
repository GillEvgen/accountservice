package com.example.accountservice.model;

import com.example.accountservice.dto.UserDto;
import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User extends UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @Size(min = 1, max = 100, message = "Имя должно содержать от 1 до 100 символов.")
    private String name;

    private String documentNumber;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts = new ArrayList<>();

    public User(Long id, @NotNull(message = "Name cannot be null") @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters") String name, @NotNull(message = "Document number cannot be null") @Size(min = 5, max = 50, message = "Document number must be between 5 and 50 characters") String documentNumber, @NotNull(message = "Document type cannot be null") @Pattern(regexp = "PASSPORT|DRIVER_LICENSE", message = "Document type must be either 'PASSPORT' or 'DRIVER_LICENSE'") DocumentType documentType) {
        super(id, name, documentNumber);
    }

    public User() {
        super(null, "User 1", "doc1");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(documentNumber, user.documentNumber) &&
                documentType == user.documentType &&
                Objects.equals(accounts, user.accounts);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                '}';
    }

    @Override

    public int hashCode() {
        return Objects.hash(id, name, documentNumber, documentType, accounts);
    }
}
