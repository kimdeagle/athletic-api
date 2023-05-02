package com.athletic.api.utils.converter;

import com.athletic.api.auth.util.Crypto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@RequiredArgsConstructor
public class CryptoConverter implements AttributeConverter<String, String> {
    private final Crypto crypto;
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return StringUtils.isNotBlank(attribute) ? crypto.encryptAES256(attribute) : "";
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return StringUtils.isNotBlank(dbData) ? crypto.decryptAES256(dbData) : "";
    }
}
