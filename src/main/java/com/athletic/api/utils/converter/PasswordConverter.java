package com.athletic.api.utils.converter;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@RequiredArgsConstructor
public class PasswordConverter implements AttributeConverter<String, String> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return StringUtils.isNotBlank(attribute) ? passwordEncoder.encode(attribute) : "";
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
