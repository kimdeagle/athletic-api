package com.athletic.api.utils.email.repository;

import com.athletic.api.utils.email.entity.EmailTemplate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

import static com.athletic.api.utils.email.entity.QEmailTemplate.emailTemplate;

@RequiredArgsConstructor
public class EmailTemplateRepositoryImpl implements EmailTemplateRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<EmailTemplate> findByCode(String code) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(emailTemplate)
                        .where(eqCode(code))
                        .fetchOne());
    }

    private BooleanExpression eqCode(String code) {
        return StringUtils.isNotBlank(code) ? emailTemplate.code.eq(code) : null;
    }
}
