package com.athletic.api.utils.excel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ExcelUtil {
    /* check integer type */
    public static boolean isIntegerType(Class<?> type) {
        List<Class<?>> integerTypes = Arrays.asList(
                Byte.class, byte.class, Short.class, short.class, Integer.class, int.class, Long.class, long.class
        );
        return integerTypes.contains(type);
    }

    /* check float type */
    public static boolean isFloatType(Class<?> type) {
        List<Class<?>> floatTypes = Arrays.asList(
                Float.class, float.class, Double.class, double.class
        );
        return floatTypes.contains(type);
    }

    /* check LocalDate type */
    public static boolean isLocalDateType(Class<?> type) {
        return type.equals(LocalDate.class);
    }

    /* check LocalDateTime type */
    public static boolean isLocalDateTimeType(Class<?> type) {
        return type.equals(LocalDateTime.class);
    }
}
