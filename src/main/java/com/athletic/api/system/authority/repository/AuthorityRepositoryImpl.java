package com.athletic.api.system.authority.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import static com.athletic.api.system.authority.entity.QAuthority.authority;


@RequiredArgsConstructor
public class AuthorityRepositoryImpl implements AuthorityRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public String findDisplayNameById(String id) {
        return queryFactory
                .select(authority.displayName)
                .from(authority)
                .where(eqId(id))
                .fetchOne();
    }

    @Override
    public boolean existsByName(String name) {
        return queryFactory
                .selectFrom(authority)
                .where(eqName(name))
                .fetchOne()
                != null;
    }

    @Override
    public boolean existsByNameAndIdNot(String name, String id) {
        return queryFactory
                .selectFrom(authority)
                .where(eqName(name), neId(id))
                .fetchOne()
                != null;
    }

    private BooleanExpression eqId(String id) {
        return StringUtils.isNotBlank(id) ? authority.id.eq(id) : null;
    }

    private BooleanExpression eqName(String name) {
        return StringUtils.isNotBlank(name) ? authority.name.eq(name) : null;
    }

    private BooleanExpression neId(String id) {
        return StringUtils.isNotBlank(id) ? authority.id.ne(id) : null;
    }
}
