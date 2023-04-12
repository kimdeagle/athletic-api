package com.athletic.api.schedule.dto;

import com.athletic.api.auth.util.SecurityUtil;
import com.athletic.api.schedule.entity.Schedule;
import com.athletic.api.util.excel.ExcelUploadColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDto {
    private String id;

    @ExcelUploadColumn(colIndex = 1, required = true)
    private LocalDateTime startDt;

    @ExcelUploadColumn(colIndex = 2, required = true)
    private LocalDateTime endDt;

    @ExcelUploadColumn(colIndex = 3, required = true)
    private String title;

    @ExcelUploadColumn(colIndex = 4)
    private String description;

    public Schedule toSchedule() {
        return Schedule.builder()
                .startDt(startDt)
                .endDt(endDt)
                .title(title)
                .description(description)
                .regId(SecurityUtil.getCurrentId())
                .regDt(LocalDateTime.now())
                .modId(SecurityUtil.getCurrentId())
                .modDt(LocalDateTime.now())
                .build();
    }

    public Schedule toUpdateSchedule() {
        return Schedule.builder()
                .id(id)
                .startDt(startDt)
                .endDt(endDt)
                .title(title)
                .description(description)
                .modId(SecurityUtil.getCurrentId())
                .modDt(LocalDateTime.now())
                .build();
    }
}
