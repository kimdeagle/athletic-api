package com.athletic.api.system.menu.repository;

import com.athletic.api.system.menu.entity.Menu;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.athletic.api.system.admin.entity.QAdmin.admin;
import static com.athletic.api.system.menu.entity.QMenu.menu;

@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Menu> findAllByAdminIdAndUseYn(String adminId, String useYn) {
        return queryFactory
                .selectFrom(menu)
                .join(admin)
                .on(hasAuthorities(admin.authorityId))
                .where(eqAdminId(adminId), eqMenuUseYn(useYn))
                .fetch();
    }

    @Override
    public List<Menu> findAllByUpMenuId(String upMenuId) {
        return queryFactory
                .selectFrom(menu)
                .where(eqUpMenuId(upMenuId))
                .fetch();
    }

    @Override
    public List<String> findAllNameByAuthorityId(String authorityId) {
        return queryFactory
                .select(menu.name)
                .from(menu)
                .where(hasAuthorities(authorityId))
                .fetch();
    }

    private BooleanExpression hasAuthorities(Object authorityId) {
        return Expressions.stringTemplate("ARRAY_POSITION({0}, {1})", menu.authorities, authorityId).isNotNull();
    }

    private BooleanExpression eqAdminId(String adminId) {
        return StringUtils.isNotBlank(adminId) ? admin.id.eq(adminId) : null;
    }

    private BooleanExpression eqMenuUseYn(String useYn) {
        return StringUtils.isNotBlank(useYn) ? menu.useYn.eq(useYn) : null;
    }

    private BooleanExpression eqUpMenuId(String upMenuId) {
        return StringUtils.isNotBlank(upMenuId) ? menu.upMenuId.eq(upMenuId) : null;
    }
}
