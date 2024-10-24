package com.example.accountservice.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Обработка всех исключений, наследующихся от AppException
    @ExceptionHandler(ApiException.class)
    public ErrorDto handleAppException(ApiException ex, WebRequest request) {
        // Логируем ошибку с уровня ERROR
        logger.error("AppException occurred: {}, Status: {}, Request: {}",
                ex.getMessage(),
                ex.getStatus(),
                request.getDescription(false)
        );

        // Установка статуса ответа в зависимости от типа исключения
        RequestContextHolder.getRequestAttributes().setAttribute(
                "org.springframework.web.servlet.HandlerMapping.produceResponse",
                ex.getStatus().value(),
                RequestAttributes.SCOPE_REQUEST
        );

        return new ErrorDto(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                ex.getStatus().value()
        );
    }

    // Обработка остальных исключений с INTERNAL_SERVER_ERROR
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleGenericException(Exception ex, WebRequest request) {
        // Логируем непредвиденные ошибки с уровня ERROR
        logger.error("Unexpected exception occurred: {}, Request: {}",
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ErrorDto(
                LocalDateTime.now(),
                "Произошла непредвиденная ошибка",
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }
}



