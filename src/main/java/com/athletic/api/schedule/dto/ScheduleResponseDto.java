package com.athletic.api.schedule.dto;

import com.athletic.api.common.dto.BaseResponseDto;
import com.athletic.api.schedule.entity.Schedule;
import com.athletic.api.util.excel.ExcelDownloadColumn;
import com.athletic.api.util.excel.style.ExcelCellStyle;
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

    @ExcelDownloadColumn(headerName = "시작일자", sort = 0, width = 20, bodyStyle = ExcelCellStyle.CENTER_BODY)
    private LocalDateTime startDt;

    @ExcelDownloadColumn(headerName = "종료일자", sort = 1, width = 20, bodyStyle = ExcelCellStyle.CENTER_BODY)
    private LocalDateTime endDt;

    @ExcelDownloadColumn(headerName = "일정명", sort = 2, width = 20)
    private String title;

    @ExcelDownloadColumn(headerName = "상세내용", sort = 3, width = 30)
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
