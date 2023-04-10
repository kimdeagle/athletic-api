package com.athletic.api.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    public static final int SUCCESS = 200;
    public static final int FAIL = 500;

    private int status;
    private String message;
    private Object data;

    public static ResponseDto success(String message) {
        return ResponseDto.builder()
                .status(SUCCESS)
                .message(message)
                .build();
    }

    public static ResponseDto success(Object data) {
        return ResponseDto.builder()
                .status(SUCCESS)
                .data(data)
                .build();
    }

    public static ResponseDto success(String message, Object data) {
        return ResponseDto.builder()
                .status(SUCCESS)
                .message(message)
                .data(data)
                .build();
    }
}
