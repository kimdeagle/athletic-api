package com.athletic.api.util.email.repository;

import com.athletic.api.util.email.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, String> {
    Optional<EmailTemplate> findByCode(String code);
}
