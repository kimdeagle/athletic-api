package com.athletic.api.util.email.controller;

import com.athletic.api.util.email.dto.EmailDto;
import com.athletic.api.util.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/email")
    public void sendEmail(EmailDto emailDto) {
        emailService.sendEmail(emailDto);
    }
}
