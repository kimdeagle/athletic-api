package com.athletic.api.schedule.dto;

import com.athletic.api.security.utils.SecurityUtils;
import com.athletic.api.schedule.entity.Schedule;
import com.athletic.api.utils.code.CodeDetail;
import com.athletic.api.utils.excel.ExcelUploadColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDto {
    private String id;

    @ExcelUploadColumn(colIndex = 1, required = true)
    private LocalDate startDt;

    @ExcelUploadColumn(colIndex = 2, required = true)
    private LocalDate endDt;

    @ExcelUploadColumn(colIndex = 3, required = true)
    private String title;

    @ExcelUploadColumn(colIndex = 4)
    private String description;

    @ExcelUploadColumn(colIndex = 5)
    private String bgColorCd;

    public Schedule toSchedule() {
        return Schedule.builder()
                .startDt(startDt)
                .endDt(endDt)
                .title(title)
                .description(description)
                .bgColorCd(StringUtils.isNotBlank(bgColorCd) ? bgColorCd : CodeDetail.BG_COLOR_BLUE.getCode())
                .regId(SecurityUtils.getCurrentId())
                .regDt(LocalDateTime.now())
                .modId(SecurityUtils.getCurrentId())
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
                .bgColorCd(StringUtils.isNotBlank(bgColorCd) ? bgColorCd : CodeDetail.BG_COLOR_BLUE.getCode())
                .modId(SecurityUtils.getCurrentId())
                .modDt(LocalDateTime.now())
                .build();
    }
}
