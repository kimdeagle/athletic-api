package com.athletic.api.utils.converter;

import com.athletic.api.security.utils.CryptoUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@RequiredArgsConstructor
public class CryptoConverter implements AttributeConverter<String, String> {
    private final CryptoUtils cryptoUtils;
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return StringUtils.isNotBlank(attribute) ? cryptoUtils.encryptAES256(attribute) : "";
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return StringUtils.isNotBlank(dbData) ? cryptoUtils.decryptAES256(dbData) : "";
    }
}
