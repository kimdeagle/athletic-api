package com.athletic.api.util.excel.style.color;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.poi.ss.usermodel.IndexedColors;

@Getter
@AllArgsConstructor
public enum ExcelColor {
    GREY(IndexedColors.GREY_25_PERCENT.index),
    WHITE(IndexedColors.WHITE.index),
    ;

    private final short colorIndex;
}
