package com.athletic.api.util.converter;

import com.athletic.api.auth.util.Crypto;
import lombok.RequiredArgsConstructor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter
@RequiredArgsConstructor
public class CryptoConverter implements AttributeConverter<String, String> {
    private final Crypto crypto;
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return Optional.ofNullable(attribute)
                .map(crypto::encryptAES256)
                .orElse("");
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData)
                .map(crypto::decryptAES256)
                .orElse("");
    }
}
