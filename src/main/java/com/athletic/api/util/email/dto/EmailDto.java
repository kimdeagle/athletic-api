package com.athletic.api.util.email.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Data
public class EmailDto {
    private String subject;
    private String text;
    private String templateCd;
    private Map<String, String> templateMap;
    private Object to;
    private MultipartFile file;
}
