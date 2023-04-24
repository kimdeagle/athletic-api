package com.athletic.api.statistics.controller;

import com.athletic.api.common.dto.ResponseDto;
import com.athletic.api.statistics.service.StatisticsSelector;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsSelector statisticsSelector;

    @GetMapping("/dues/this-month")
    public ResponseEntity<ResponseDto> getDuesStatisticsOfThisMonth() {
        return ResponseEntity.ok(statisticsSelector.getDuesStatisticsOfThisMonth());
    }

    @GetMapping("/dashboard/member")
    public ResponseEntity<ResponseDto> getMemberStatisticsOfDashboard() {
        return ResponseEntity.ok(statisticsSelector.getMemberStatisticsOneYearAgo());
    }

    @GetMapping("/dashboard/dues")
    public ResponseEntity<ResponseDto> getDuesStatisticsOfDashboard() {
        return ResponseEntity.ok(statisticsSelector.getDuesStatisticsOneYearAgo());
    }

}
