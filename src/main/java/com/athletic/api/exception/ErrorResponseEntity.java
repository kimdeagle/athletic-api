package com.athletic.api.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseEntity {
    private int status;
    private String code;
    private String message;
}
