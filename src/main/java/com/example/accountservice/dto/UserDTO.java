package com.example.accountservice.dto;

import com.example.accountservice.model.DocumentType;

import java.util.Objects;

public class UserDTO {

   private Long id;
    private String name;
    private String documentNumber;
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
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) &&
                Objects.equals(name, userDTO.name) &&
                Objects.equals(documentNumber, userDTO.documentNumber) &&
                Objects.equals(documentType, userDTO.documentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, documentNumber, documentType);
    }
}