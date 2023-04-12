package com.athletic.api.member.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import static com.athletic.api.member.entity.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByNameAndMobileNo(String name, String mobileNo) {
        return queryFactory
                .selectFrom(member)
                .where(eqName(name), eqMobileNo(mobileNo))
                .fetchFirst()
                != null;

    }

    private BooleanExpression eqName(String name) {
        return StringUtils.isNotBlank(name) ? member.name.eq(name) : null;
    }

    private BooleanExpression eqMobileNo(String mobileNo) {
        return StringUtils.isNotBlank(mobileNo) ? member.mobileNo.eq(mobileNo) : null;
    }
}
