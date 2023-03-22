package com.athletic.api.util.excel;

import com.athletic.api.util.excel.style.ExcelCellStyle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
    String headerName() default "";
    int sort();
    int width() default 15;
    ExcelCellStyle headerStyle() default ExcelCellStyle.DEFAULT_HEADER;
    ExcelCellStyle bodyStyle() default ExcelCellStyle.DEFAULT_BODY;
}
