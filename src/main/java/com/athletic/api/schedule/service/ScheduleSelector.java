package com.athletic.api.schedule.service;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.schedule.dto.ScheduleResponseDto;
import com.athletic.api.schedule.repository.ScheduleRepository;
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
}
