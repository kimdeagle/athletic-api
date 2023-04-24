package com.athletic.api.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DuesStatisticsRequestDto {
    private String in;
    private String out;
    private String rest;
}
