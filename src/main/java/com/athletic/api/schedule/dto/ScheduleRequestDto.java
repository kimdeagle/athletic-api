package com.athletic.api.schedule.dto;

import com.athletic.api.auth.util.SecurityUtil;
import com.athletic.api.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDto {
    private String id;
    private LocalDateTime startDt;
    private LocalDateTime endDt;
    private String title;
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
