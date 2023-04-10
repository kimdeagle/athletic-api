package com.athletic.api.schedule.dto;

import com.athletic.api.common.dto.BaseResponseDto;
import com.athletic.api.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDto extends BaseResponseDto {
    private String id;
    private LocalDateTime startDt;
    private LocalDateTime endDt;
    private String title;
    private String description;

    public static ScheduleResponseDto of(Schedule schedule) {
        return ScheduleResponseDto.builder()
                .id(schedule.getId())
                .startDt(schedule.getStartDt())
                .endDt(schedule.getEndDt())
                .title(schedule.getTitle())
                .description(schedule.getDescription())
                .regId(schedule.getRegId())
                .regDt(schedule.getRegDt())
                .modId(schedule.getModId())
                .modDt(schedule.getModDt())
                .build();
    }
}
