package com.athletic.api.schedule.service;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.common.message.SuccessMessage;
import com.athletic.api.schedule.dto.ScheduleRequestDto;
import com.athletic.api.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
