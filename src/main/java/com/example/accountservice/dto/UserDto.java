package com.example.accountservice.dto;

import com.example.accountservice.type.DocumentType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UserDto {

    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @NotNull(message = "Document number cannot be null")
    @Size(min = 5, max = 50, message = "Document number must be between 5 and 50 characters")
    private String documentNumber;

    @NotNull(message = "Document type cannot be null")
    @Pattern(regexp = "PASSPORT|DRIVER_LICENSE", message = "Document type must be either 'PASSPORT' or 'DRIVER_LICENSE'")
    private DocumentType documentType;

    // Getters и Setters
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDTO = (UserDto) o;
        return Objects.equals(id, userDTO.id) &&
                Objects.equals(name, userDTO.name) &&
                Objects.equals(documentNumber, userDTO.documentNumber) &&
                Objects.equals(documentType, userDTO.documentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, documentNumber, documentType);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", documentNumber='" + documentNumber + '\'';
    }
}