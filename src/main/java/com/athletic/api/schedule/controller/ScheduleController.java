package com.athletic.api.schedule.controller;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.schedule.dto.ScheduleRequestDto;
import com.athletic.api.schedule.service.ScheduleSelector;
import com.athletic.api.schedule.service.ScheduleService;
import com.athletic.api.util.excel.ExcelDownloadSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleSelector scheduleSelector;
    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<ResponseDto> getScheduleList() {
        return ResponseEntity.ok(scheduleSelector.getScheduleList());
    }

    @PostMapping
    public ResponseEntity<ResponseDto> addSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return ResponseEntity.ok(scheduleService.addSchedule(scheduleRequestDto));
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteSchedule(@PathVariable("id") String id) {
        return ResponseEntity.ok(scheduleService.deleteSchedule(id));
    }

    @PostMapping("/excel/download")
    public void downloadExcel(@RequestBody ExcelDownloadSearchCondition condition) { scheduleSelector.downloadExcel(condition); }

    @PostMapping("/excel/upload")
    public ResponseEntity<ResponseDto> uploadExcel(MultipartFile file) { return ResponseEntity.ok(scheduleService.uploadExcel(file)); }

}
