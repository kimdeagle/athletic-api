package com.athletic.api.util.email.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {
    private String subject;
    private String text;
    private String templateCd;
    private Map<String, String> templateMap;
    private Object to;
    private MultipartFile file;
}
