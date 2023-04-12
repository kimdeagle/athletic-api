package com.athletic.api.dues.repository;

import com.athletic.api.dues.dto.DuesResponseDto;
import com.athletic.api.dues.entity.Dues;
import com.athletic.api.util.code.CodeGroup;
import com.athletic.api.util.excel.ExcelDownloadSearchCondition;
import com.athletic.api.util.querydsl.QuerydslUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.athletic.api.dues.entity.QDues.dues;

@RequiredArgsConstructor
public class DuesRepositoryImpl implements DuesRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Dues> findAllByExcelDownloadSearchCondition(ExcelDownloadSearchCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();
        if (condition.getStartDt() != null && condition.getEndDt() != null) {
            builder.and(QuerydslUtil.dateTimeToDate(dues.startDt).loe(condition.getEndDt().toLocalDate())).and(QuerydslUtil.dateTimeToDate(dues.endDt).goe(condition.getStartDt().toLocalDate()));
        }
        if (StringUtils.isNotBlank(condition.getInOutCd())) {
            builder.and(eqInOutCd(condition.getInOutCd()));
        }
        if (StringUtils.isNotBlank(condition.getInOutDtlCd())) {
            builder.and(eqInOutDtlCd(condition.getInOutDtlCd()));
        }

        return queryFactory
                .selectFrom(dues)
                .where(builder)
                .fetch();
    }

    @Override
    public List<DuesResponseDto> findAmountThisMonth() {
        List<DuesResponseDto> list =
                queryFactory
                        .select(Projections.fields(DuesResponseDto.class,
                                dues.inOutCd,
                                dues.amount.sum().as("amount")))
                        .from(dues)
                        .where(betweenUntilMonth())
                        .groupBy(dues.inOutCd)
                        .fetch();

        List<DuesResponseDto> restList =
                queryFactory
                        .select(Projections.fields(DuesResponseDto.class,
                                Expressions.asString(CodeGroup.IN_OUT_REST.getCode()).as("inOutCd"),
                                Expressions.cases()
                                        .when(eqInOutCd(CodeGroup.IN_OUT_IN.getCode()))
                                        .then(dues.amount)
                                        .otherwise(dues.amount.multiply(-1))
                                        .sum().as("amount")))
                        .from(dues)
                        .fetch();

        list.addAll(restList);

        return list;
    }

    private BooleanExpression betweenUntilMonth() {
        return QuerydslUtil.truncDateTimeUntilMonth(Expressions.currentTimestamp())
                .between(QuerydslUtil.truncDateTimeUntilMonth(dues.startDt), QuerydslUtil.truncDateTimeUntilMonth(dues.endDt));
    }

    private BooleanExpression eqInOutCd(String inOutCd) {
        return StringUtils.isNotBlank(inOutCd) ? dues.inOutCd.eq(inOutCd) : null;
    }

    private BooleanExpression eqInOutDtlCd(String inOutDtlCd) {
        return StringUtils.isNotBlank(inOutDtlCd) ? dues.inOutDtlCd.eq(inOutDtlCd) : null;
    }
}
