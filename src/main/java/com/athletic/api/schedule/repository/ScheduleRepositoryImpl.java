package com.athletic.api.schedule.repository;

import com.athletic.api.schedule.entity.Schedule;
import com.athletic.api.util.excel.ExcelDownloadSearchCondition;
import com.athletic.api.util.querydsl.QuerydslUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
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

    private BooleanExpression loeStartDt(LocalDateTime right) {
        return right != null ? QuerydslUtil.dateTimeToDate(schedule.startDt).loe(right.toLocalDate()) : null;
    }

    private BooleanExpression goeEndDt(LocalDateTime right) {
        return right != null ? QuerydslUtil.dateTimeToDate(schedule.endDt).goe(right.toLocalDate()) : null;
    }
}
