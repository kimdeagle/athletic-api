package com.athletic.api.util.email.handler;

import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

public class EmailHandler {
    private final JavaMailSender javaMailSender;
    private final MimeMessage mimeMessage;
    private final MimeMessageHelper mimeMessageHelper;

    public EmailHandler(JavaMailSender javaMailSender) throws MessagingException {
        this.javaMailSender = javaMailSender;
        mimeMessage = javaMailSender.createMimeMessage();
        mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
    }

    public void setFrom(String from) throws MessagingException {
        mimeMessageHelper.setFrom(from);
    }

    public void setTo(String email) throws MessagingException {
        mimeMessageHelper.setTo(email);
    }

    public void setTo(String[] emails) throws MessagingException {
        mimeMessageHelper.setTo(emails);
    }

    public void setSubject(String subject) throws MessagingException {
        mimeMessageHelper.setSubject(subject);
    }

    public void setText(String text, boolean useHtml) throws MessagingException {
        mimeMessageHelper.setText(text, useHtml);
    }

    public void addAttach(String fileName, MultipartFile file) throws MessagingException {
        mimeMessageHelper.addAttachment(fileName, file);
    }

    public void addInline(String contentId, MultipartFile file) throws IOException, MessagingException {
        mimeMessageHelper.addInline(contentId, new ByteArrayResource(file.getBytes()), "image/jpeg");
    }

    public void send() {
        javaMailSender.send(mimeMessage);
    }

}
