package com.athletic.api.util.excel.style.align;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

@Getter
@AllArgsConstructor
public enum ExcelAlign {
    LEFT_TOP(HorizontalAlignment.LEFT, VerticalAlignment.TOP),
    LEFT_CENTER(HorizontalAlignment.LEFT, VerticalAlignment.CENTER),
    LEFT_BOTTOM(HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM),
    CENTER_TOP(HorizontalAlignment.CENTER, VerticalAlignment.TOP),
    CENTER_CENTER(HorizontalAlignment.CENTER, VerticalAlignment.CENTER),
    CENTER_BOTTOM(HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM),
    RIGHT_TOP(HorizontalAlignment.RIGHT, VerticalAlignment.TOP),
    RIGHT_CENTER(HorizontalAlignment.RIGHT, VerticalAlignment.CENTER),
    RIGHT_BOTTOM(HorizontalAlignment.RIGHT, VerticalAlignment.BOTTOM),
    GENERAL(HorizontalAlignment.GENERAL, VerticalAlignment.CENTER),
    ;

    private final HorizontalAlignment horizontalAlignment;
    private final VerticalAlignment verticalAlignment;

}
