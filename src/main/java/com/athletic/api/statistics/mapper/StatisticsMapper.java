package com.athletic.api.statistics.mapper;

import com.athletic.api.statistics.dto.DuesStatisticsRequestDto;
import com.athletic.api.statistics.dto.DuesStatisticsResponseDto;
import com.athletic.api.statistics.dto.MemberStatisticsResponseDto;

import java.util.List;

public interface StatisticsMapper {
    List<DuesStatisticsResponseDto> getDuesStatisticsOfThisMonth(DuesStatisticsRequestDto duesStatisticsRequestDto);
    List<MemberStatisticsResponseDto> getMemberStatisticsOneYearAgo();
    List<DuesStatisticsResponseDto> getDuesStatisticsOneYearAgo(DuesStatisticsRequestDto duesStatisticsRequestDto);

}
