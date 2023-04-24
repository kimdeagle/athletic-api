package com.athletic.api.dues.repository;

import com.athletic.api.dues.entity.Dues;
import com.athletic.api.util.excel.ExcelDownloadSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.List;

import static com.athletic.api.dues.entity.QDues.dues;

@RequiredArgsConstructor
public class DuesRepositoryImpl implements DuesRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Dues> findAllByExcelDownloadSearchCondition(ExcelDownloadSearchCondition condition) {
        return queryFactory
                .selectFrom(dues)
                .where(loeStartDt(condition.getEndDt()),
                        goeEndDt(condition.getStartDt()),
                        eqInOutCd(condition.getInOutCd()),
                        eqInOutDtlCd(condition.getInOutDtlCd()))
                .orderBy(dues.startDt.asc(),
                        dues.endDt.asc(),
                        dues.amount.asc())
                .fetch();
    }

    private BooleanExpression loeStartDt(LocalDate right) {
        return right != null ? dues.startDt.loe(right) : null;
    }

    private BooleanExpression goeEndDt(LocalDate right) {
        return right != null ? dues.endDt.goe(right) : null;
    }

    private BooleanExpression eqInOutCd(String inOutCd) {
        return StringUtils.isNotBlank(inOutCd) ? dues.inOutCd.eq(inOutCd) : null;
    }

    private BooleanExpression eqInOutDtlCd(String inOutDtlCd) {
        return StringUtils.isNotBlank(inOutDtlCd) ? dues.inOutDtlCd.eq(inOutDtlCd) : null;
    }
}
