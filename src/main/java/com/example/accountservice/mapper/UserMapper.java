package com.example.accountservice.mapper;


import com.example.accountservice.dto.UserDto;
import com.example.accountservice.model.User;
import com.example.accountservice.type.DocumentType;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Преобразование User в UserDTO
    public UserDto toDto(User user) {
        UserDto dto = new UserDto(1L, "User 1", "1", DocumentType.PASSPORT);
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setDocumentNumber(user.getDocumentNumber());
        dto.setDocumentType(user.getDocumentType());
        return dto;
    }

    // Преобразование UserDTO в User
    public User toEntity(UserDto dto) {
        User user = new User(1L, "name", "1", DocumentType.PASSPORT);
        user.setName(dto.getName());
        user.setDocumentNumber(dto.getDocumentNumber());
        user.setDocumentType(dto.getDocumentType());
        return user;
    }
}