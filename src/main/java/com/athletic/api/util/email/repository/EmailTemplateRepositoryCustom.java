package com.athletic.api.util.email.repository;

import com.athletic.api.util.email.entity.EmailTemplate;

import java.util.Optional;

public interface EmailTemplateRepositoryCustom {
    Optional<EmailTemplate> findByCode(String code);
}
