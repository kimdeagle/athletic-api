package com.athletic.api.system.menu.repository;

import com.athletic.api.system.menu.entity.Menu;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.athletic.api.admin.entity.QAdmin.admin;
import static com.athletic.api.system.menu.entity.QMenu.menu;

@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Menu> findAllByAdminIdAndUseYn(String adminId, String useYn) {
        return queryFactory
                .selectFrom(menu)
                .join(admin)
                .on(hasAuthorities())
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

    private BooleanExpression hasAuthorities() {
        return Expressions.numberTemplate(int.class, "ARRAY_POSITION({0}, {1})", menu.authorities, admin.authorityId).isNotNull();
    }

    private BooleanExpression eqAdminId(String id) {
        return StringUtils.isNotBlank(id) ? admin.id.eq(id) : null;
    }

    private BooleanExpression eqMenuUseYn(String useYn) {
        return StringUtils.isNotBlank(useYn) ? menu.useYn.eq(useYn) : null;
    }

    private BooleanExpression eqUpMenuId(String upMenuId) {
        return StringUtils.isNotBlank(upMenuId) ? menu.upMenuId.eq(upMenuId) : null;
    }
}
