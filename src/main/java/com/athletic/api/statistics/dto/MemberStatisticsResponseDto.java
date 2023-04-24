package com.athletic.api.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberStatisticsResponseDto {
    private String displayDate;
    private Long joinCount;
    private Long totalCount;
}
