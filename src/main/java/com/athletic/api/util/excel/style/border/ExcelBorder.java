package com.athletic.api.util.excel.style.border;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.poi.ss.usermodel.BorderStyle;

@Getter
@AllArgsConstructor
public enum ExcelBorder {
    THIN(BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN),
    ;
    private final BorderStyle borderTop;
    private final BorderStyle borderRight;
    private final BorderStyle borderBottom;
    private final BorderStyle borderLeft;
}
