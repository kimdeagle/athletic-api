package com.athletic.api.utils.converter;

import com.athletic.api.auth.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@RequiredArgsConstructor
public class PasswordConverter implements AttributeConverter<String, String> {
    private final PasswordUtils passwordUtils;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return StringUtils.isNotBlank(attribute) ? passwordUtils.getPasswordEncoder().encode(attribute) : "";
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
