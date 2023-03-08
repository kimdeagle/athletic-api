package com.athletic.api.util.excel.style;

import com.athletic.api.util.excel.style.align.ExcelAlign;
import com.athletic.api.util.excel.style.border.ExcelBorder;
import com.athletic.api.util.excel.style.color.ExcelColor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExcelCellStyle {
    DEFAULT_HEADER(ExcelColor.GREY, ExcelBorder.THIN, ExcelAlign.CENTER_CENTER),
    DEFAULT_BODY(ExcelColor.WHITE, ExcelBorder.THIN, ExcelAlign.GENERAL),
    TEST_BODY(ExcelColor.WHITE, ExcelBorder.THIN, ExcelAlign.RIGHT_CENTER),
    ;

    private final ExcelColor excelColor;
    private final ExcelBorder excelBorder;
    private final ExcelAlign excelAlign;
}
