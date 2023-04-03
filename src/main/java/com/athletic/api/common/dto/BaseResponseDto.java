package com.athletic.api.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseResponseDto {
    private String regId;

    private LocalDateTime regDt;

    private String modId;

    private LocalDateTime modDt;
}
