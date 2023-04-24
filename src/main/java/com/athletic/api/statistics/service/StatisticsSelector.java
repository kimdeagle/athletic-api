package com.athletic.api.statistics.service;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.statistics.dto.DuesStatisticsRequestDto;
import com.athletic.api.statistics.mapper.StatisticsMapper;
import com.athletic.api.util.code.CodeGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticsSelector {
    private final StatisticsMapper statisticsMapper;

    public ResponseDto getDuesStatisticsOfThisMonth() {
        return ResponseDto.success(statisticsMapper.getDuesStatisticsOfThisMonth(DuesStatisticsRequestDto.builder().in(CodeGroup.IN_OUT_IN.getCode()).rest(CodeGroup.IN_OUT_REST.getCode()).build()));
    }

    public ResponseDto getMemberStatisticsOneYearAgo() {
        return ResponseDto.success(statisticsMapper.getMemberStatisticsOneYearAgo());
    }

    public ResponseDto getDuesStatisticsOneYearAgo() {
        return ResponseDto.success(statisticsMapper.getDuesStatisticsOneYearAgo(DuesStatisticsRequestDto.builder().in(CodeGroup.IN_OUT_IN.getCode()).out(CodeGroup.IN_OUT_OUT.getCode()).build()));
    }

}
