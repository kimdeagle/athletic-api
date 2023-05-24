package com.athletic.api.utils.converter;

import com.athletic.api.security.utils.CryptoUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@RequiredArgsConstructor
public class MobileNoConverter implements AttributeConverter<String, String> {
    private final CryptoUtils cryptoUtils;
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return StringUtils.isNotBlank(attribute) ? cryptoUtils.encryptAES256(attribute.replaceAll("[^0-9]", "")) : "";
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return StringUtils.isNotBlank(dbData) ? insertHyphenMobileNo(cryptoUtils.decryptAES256(dbData)) : "";
    }

    private String insertHyphenMobileNo(String mobileNo) {
        return mobileNo.substring(0, 3) + "-" + mobileNo.substring(3, 7) + "-" + mobileNo.substring(7);
    }
}
