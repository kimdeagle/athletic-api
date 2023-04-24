package com.athletic.api.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DuesStatisticsResponseDto {
    private String displayDate;
    private Long inAmount;
    private Long outAmount;
    private Long restAmount;

    private String inOutCd;
    private Long amount;
}
