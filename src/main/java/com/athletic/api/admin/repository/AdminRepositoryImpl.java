package com.athletic.api.admin.repository;

import com.athletic.api.admin.entity.Admin;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

import static com.athletic.api.admin.entity.QAdmin.admin;

@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByLoginId(String loginId) {
        return queryFactory
                .selectFrom(admin)
                .where(eqLoginId(loginId))
                .fetchFirst()
                != null;
    }

    @Override
    public Optional<Admin> findByLoginId(String loginId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(admin)
                        .where(eqLoginId(loginId))
                        .fetchOne()
        );
    }

    @Override
    public Optional<Admin> findByLoginIdAndEmail(String loginId, String email) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(admin)
                        .where(eqLoginId(loginId), eqEmail(email))
                        .fetchOne()
        );
    }

    private BooleanExpression eqLoginId(String loginId) {
        return StringUtils.isNotBlank(loginId) ? admin.loginId.eq(loginId) : null;
    }

    private BooleanExpression eqEmail(String email) {
        return StringUtils.isNotBlank(email) ? admin.email.eq(email) : null;
    }
}
