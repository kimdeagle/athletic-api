package com.athletic.api.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;
    private final Object parameter;

    public CustomException(ErrorCode errorCode, String... parameter) {
        this.errorCode = errorCode;
        this.parameter = parameter;
    }

    public CustomException(ErrorCode errorCode, List<String> parameter) {
        this.errorCode = errorCode;
        this.parameter = parameter;
    }
}
