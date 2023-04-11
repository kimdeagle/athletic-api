package com.athletic.api.system.menu.repository;

import com.athletic.api.auth.util.SecurityUtil;
import com.athletic.api.system.menu.entity.Menu;
import com.athletic.api.util.constant.Const;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.athletic.api.admin.entity.QAdmin.admin;
import static com.athletic.api.system.menu.entity.QMenu.menu;

@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Menu> findAllByAuthorityIdAndUseYn() {
        return queryFactory
                .selectFrom(menu)
                .join(admin)
                .on(hasAuthorities())
                .where(eqAdminId(SecurityUtil.getCurrentId()), eqMenuUseYn(Const.USE_YN_Y))
                .fetch();
    }

    private BooleanExpression hasAuthorities() {
        return Expressions.numberTemplate(int.class, "ARRAY_POSITION({0}, {1})", menu.authorities, admin.authorityId).isNotNull();
    }

    private BooleanExpression eqAdminId(String id) {
        return admin.id.eq(id);
    }

    private BooleanExpression eqMenuUseYn(String useYn) {
        return menu.useYn.eq(useYn);
    }
}
