package com.athletic.api.schedule.service;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.common.message.SuccessMessage;
import com.athletic.api.schedule.dto.ScheduleRequestDto;
import com.athletic.api.schedule.repository.ScheduleRepository;
import com.athletic.api.util.excel.ExcelReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ResponseDto addSchedule(ScheduleRequestDto scheduleRequestDto) {
        scheduleRepository.save(scheduleRequestDto.toSchedule());

        return ResponseDto.success(SuccessMessage.Schedule.ADD_SCHEDULE);
    }

    public ResponseDto updateSchedule(ScheduleRequestDto scheduleRequestDto) {
        scheduleRepository.save(scheduleRequestDto.toUpdateSchedule());

        return ResponseDto.success(SuccessMessage.Schedule.UPDATE_SCHEDULE);
    }

    public ResponseDto deleteSchedule(String id) {
        scheduleRepository.deleteById(id);

        return ResponseDto.success(SuccessMessage.Schedule.DELETE_SCHEDULE);
    }

    public ResponseDto uploadExcel(MultipartFile file) {
        List<ScheduleRequestDto> excelList = ExcelReader.read(file, ScheduleRequestDto.class);

        scheduleRepository.saveAll(excelList.stream().map(ScheduleRequestDto::toSchedule).collect(Collectors.toList()));

        return ResponseDto.success(SuccessMessage.Excel.UPLOAD);
    }
}
