package com.athletic.api.utils.email.repository;

import com.athletic.api.utils.email.entity.EmailTemplate;

import java.util.Optional;

public interface EmailTemplateRepositoryCustom {
    Optional<EmailTemplate> findByCode(String code);
}
