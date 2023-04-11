package com.athletic.api.util.querydsl;

import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Querydsl 유틸 (Postgresql 함수 사용)
 */
public class QuerydslUtil {
    public static DateTemplate<LocalDate> dateTimeToDate(Object dateTime) {
        return Expressions.dateTemplate(LocalDate.class, "DATE({0})", dateTime);
    }

    public static DateTemplate<LocalDateTime> truncDateTimeUntilMonth(Object dateTime) {
        return Expressions.dateTemplate(LocalDateTime.class, "DATE_TRUNC('MONTH', {0})", dateTime);
    }
}
