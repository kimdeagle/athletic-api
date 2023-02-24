package com.athletic.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(CustomException e) {
        if (log.isErrorEnabled())
            log.error("CustomException : {} ({})", e.getErrorCode().name(), e.getErrorCode().getMessage());
        return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
    }
}
