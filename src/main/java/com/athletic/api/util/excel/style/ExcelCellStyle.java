package com.athletic.api.util.excel.style;

import com.athletic.api.util.excel.style.align.ExcelAlign;
import com.athletic.api.util.excel.style.border.ExcelBorder;
import com.athletic.api.util.excel.style.color.ExcelColor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.poi.ss.usermodel.FillPatternType;

@Getter
@AllArgsConstructor
public enum ExcelCellStyle {
    DEFAULT_HEADER(ExcelColor.GREY, FillPatternType.SOLID_FOREGROUND, ExcelBorder.THIN, ExcelAlign.CENTER_CENTER),
    DEFAULT_BODY(ExcelColor.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelBorder.THIN, ExcelAlign.GENERAL),
    TEST_BODY(ExcelColor.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelBorder.THIN, ExcelAlign.RIGHT_CENTER),
    CENTER_BODY(ExcelColor.WHITE, FillPatternType.SOLID_FOREGROUND, ExcelBorder.THIN, ExcelAlign.CENTER_CENTER),
    ;

    private final ExcelColor color;
    private final FillPatternType fillPattern;
    private final ExcelBorder border;
    private final ExcelAlign align;
}
