package com.athletic.api.schedule.repository;

import com.athletic.api.schedule.dto.ScheduleResponseDto;
import com.athletic.api.schedule.entity.Schedule;
import com.athletic.api.utils.excel.ExcelDownloadSearchCondition;

import java.util.List;

public interface ScheduleRepositoryCustom {
    List<Schedule> findAllByExcelDownloadSearchCondition(ExcelDownloadSearchCondition condition);
    List<ScheduleResponseDto> findTop5OrderByEndDtDesc();
}
