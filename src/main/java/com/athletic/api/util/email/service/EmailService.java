package com.athletic.api.util.email.service;

import com.athletic.api.util.email.dto.EmailDto;
import com.athletic.api.util.email.entity.EmailTemplate;
import com.athletic.api.util.email.handler.EmailHandler;
import com.athletic.api.util.email.repository.EmailTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final EmailTemplateRepository templateRepository;

    @Value("${spring.mail.username}")
    private String from;

    private void processTemplate(EmailDto emailDto) {
        EmailTemplate template = templateRepository.findById(emailDto.getTemplateCd()).orElseThrow(() -> new RuntimeException("cannot find email template"));

        emailDto.setSubject(template.getTitle());

        if (ObjectUtils.isNotEmpty(emailDto.getTemplateMap())) {
            emailDto.getTemplateMap().forEach((key, value) -> {
                emailDto.setText(template.getContent().replaceAll("\\{" + key + "}", value));
            });
        }
    }

    public void sendEmail(EmailDto emailDto) {
        try {
            boolean useHtml = StringUtils.isNotBlank(emailDto.getTemplateCd());

            if (useHtml) processTemplate(emailDto);

            EmailHandler emailHandler = new EmailHandler(javaMailSender);

            emailHandler.setFrom(from);
            Object to = emailDto.getTo();
            if (to instanceof String) emailHandler.setTo((String) to);
            if (to instanceof String[]) emailHandler.setTo((String[]) to);
            emailHandler.setSubject(emailDto.getSubject());
            emailHandler.setText(emailDto.getText(), useHtml);
            emailHandler.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
