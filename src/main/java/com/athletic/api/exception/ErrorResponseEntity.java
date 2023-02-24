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

    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(ErrorCode e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponseEntity.builder()
                        .status(e.getStatus())
                        .error(e.name())
                        .code(e.name())
                        .message(e.getMessage())
                        .build());
    }
}
