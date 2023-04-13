package com.athletic.api.schedule.repository;

import com.athletic.api.schedule.entity.Schedule;
import com.athletic.api.util.excel.ExcelDownloadSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.athletic.api.schedule.entity.QSchedule.schedule;

@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Schedule> findAllByExcelDownloadSearchCondition(ExcelDownloadSearchCondition condition) {
        return queryFactory
                .selectFrom(schedule)
                .where(loeStartDt(condition.getEndDt()),
                        goeEndDt(condition.getStartDt()))
                .orderBy(schedule.startDt.asc(),
                        schedule.endDt.asc())
                .fetch();
    }

    private BooleanExpression loeStartDt(LocalDate right) {
        return right != null ? schedule.startDt.loe(right) : null;
    }

    private BooleanExpression goeEndDt(LocalDate right) {
        return right != null ? schedule.endDt.goe(right) : null;
    }
}
