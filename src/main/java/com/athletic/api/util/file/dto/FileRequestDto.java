package com.athletic.api.util.file.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class FileRequestDto {
    private String sampleName;

    public String getSampleName() {
        return convertCamelToSnake(sampleName);
    }

    private String convertCamelToSnake(String value) {
        StringBuilder result = new StringBuilder();
        for (int i=0; i<value.length(); i++) {
            char c = value.charAt(i);
            if (Character.isUpperCase(c)) {
                result.append("_");
            }
            result.append(c);
        }
        return result.toString().toUpperCase();
    }
}
