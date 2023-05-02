package com.athletic.api.schedule.service;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.schedule.dto.ScheduleResponseDto;
import com.athletic.api.schedule.repository.ScheduleRepository;
import com.athletic.api.utils.excel.ExcelDownloadSearchCondition;
import com.athletic.api.utils.excel.ExcelWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleSelector {
    private final ScheduleRepository scheduleRepository;

    public ResponseDto getScheduleList() {
        List<ScheduleResponseDto> list = scheduleRepository.findAll().stream().map(ScheduleResponseDto::of).collect(Collectors.toList());

        return ResponseDto.success(list);
    }

    public void downloadExcel(ExcelDownloadSearchCondition condition) {
        List<ScheduleResponseDto> list =
                scheduleRepository.findAllByExcelDownloadSearchCondition(condition)
                        .stream().map(ScheduleResponseDto::of)
                        .collect(Collectors.toList());

        ExcelWriter.write("일정 리스트", list, ScheduleResponseDto.class);
    }

    public ResponseDto getScheduleListOfDashboard() {
        List<ScheduleResponseDto> list = scheduleRepository.findTop5OrderByEndDtDesc();

        return ResponseDto.success(list);
    }
}
