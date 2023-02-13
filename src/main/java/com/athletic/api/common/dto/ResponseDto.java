package com.athletic.api.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
//TODO temp response dto (추후 수정)
public class ResponseDto {
    public static final int SUCCESS = 200;
    public static final int FAIL = 500;

    private int code;
    private String message;
}
