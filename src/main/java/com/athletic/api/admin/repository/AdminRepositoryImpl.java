package com.athletic.api.admin.repository;

import com.athletic.api.admin.dto.AdminResponseDto;
import com.athletic.api.admin.entity.Admin;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.athletic.api.admin.entity.QAdmin.admin;
import static com.athletic.api.system.authority.entity.QAuthority.authority;

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
                        .fetchOne());
    }

    @Override
    public Optional<Admin> findByLoginIdAndEmail(String loginId, String email) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(admin)
                        .where(eqLoginId(loginId), eqEmail(email))
                        .fetchOne());
    }

    @Override
    public List<String> findAllNameByAuthorityId(String authorityId) {
        return queryFactory
                .select(admin.name)
                .from(admin)
                .where(eqAuthorityId(authorityId))
                .fetch();
    }

    @Override
    public Optional<AdminResponseDto> getUserById(String id) {
        return Optional.ofNullable(
                queryFactory
                        .select(Projections.fields(
                                AdminResponseDto.class,
                                admin.id,
                                admin.name,
                                admin.authorityId,
                                authority.displayName.as("authorityDisplayName")))
                        .from(admin)
                        .join(authority)
                        .on(eqId(id), admin.authorityId.eq(authority.id))
                        .fetchOne());
    }

    private BooleanExpression eqId(String id) {
        return StringUtils.isNotBlank(id) ? admin.id.eq(id) : null;
    }

    private BooleanExpression eqLoginId(String loginId) {
        return StringUtils.isNotBlank(loginId) ? admin.loginId.eq(loginId) : null;
    }

    private BooleanExpression eqEmail(String email) {
        return StringUtils.isNotBlank(email) ? admin.email.eq(email) : null;
    }

    private BooleanExpression eqAuthorityId(String authorityId) {
        return StringUtils.isNotBlank(authorityId) ? admin.authorityId.eq(authorityId) : null;
    }
}
