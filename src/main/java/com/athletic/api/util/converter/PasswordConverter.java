package com.athletic.api.util.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter
@RequiredArgsConstructor
public class PasswordConverter implements AttributeConverter<String, String> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return Optional.ofNullable(attribute).map(passwordEncoder::encode).orElse(null);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
