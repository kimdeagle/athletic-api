package com.athletic.api.util.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelUploadColumn {
    int colIndex();
    boolean required() default false;
    String validationRegex() default "";
    String errorMessage() default "";
}
