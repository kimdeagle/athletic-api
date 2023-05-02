package com.athletic.api.utils.converter;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringDateConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return StringUtils.isNotBlank(attribute) ? attribute.replaceAll("[^0-9]", "") : "";
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return StringUtils.isNotBlank(dbData) ? dbData.substring(0, 4) + "-" + dbData.substring(4, 6) + "-" + dbData.substring(6) : "";
    }
}
