package com.athletic.api.schedule.repository;

import com.athletic.api.schedule.entity.Schedule;
import com.athletic.api.util.excel.ExcelDownloadSearchCondition;

import java.util.List;

public interface ScheduleRepositoryCustom {
    List<Schedule> findAllByExcelDownloadSearchCondition(ExcelDownloadSearchCondition condition);
}
