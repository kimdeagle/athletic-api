package com.athletic.api.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponseEntity {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String code;
    private String error;
    private String message;

    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(int status, String name, String message) {
        return ResponseEntity
                .status(status)
                .body(ErrorResponseEntity.builder()
                        .status(status)
                        .error(name)
                        .code(name)
                        .message(message)
                        .build());
    }
}
