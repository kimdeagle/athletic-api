package com.athletic.api.util.excel;

import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.exception.ErrorMessage;
import com.athletic.api.util.constant.Const;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelReader {

    private static Workbook workbook;
    private static Sheet sheet;
    private static int rowCount;
    private static List<Field> fields;
    private static Row row;
    private static Cell cell;

    private static final int SHEET_INDEX = 0;
    private static final int ROW_START_INDEX = 2;
    private static final int COLUMN_START_INDEX = 1;

    private static int rowIndex;
    private static int colIndex;

    /* read excel */
    public static <T> List<T> read(MultipartFile file, Class<T> clazz) {
        validationFile(file);
        try {
            File tempFile = File.createTempFile("temp", ".tmp");
            file.transferTo(tempFile);
            try (InputStream is = new FileInputStream(tempFile)) {
                workbook = new XSSFWorkbook(is);
                sheet = workbook.getSheetAt(SHEET_INDEX);
                rowCount = sheet.getPhysicalNumberOfRows() - 1;
                fields = getUploadFields(clazz);
                return extractListFromExcel(clazz);
            } catch (IOException e) {
                throw new CustomException(ErrorCode.INVALID_EXCEL_UPLOAD_FILE);
            } finally {
                FileUtils.deleteQuietly(tempFile);
            }
        } catch (IOException e) {
            throw new CustomException(ErrorCode.INVALID_EXCEL_UPLOAD_FILE);
        }
    }

    /* validation file */
    private static void validationFile(MultipartFile file) {
        if (file == null)
            throw new CustomException(ErrorCode.EMPTY_EXCEL_UPLOAD_FILE);

        if (!StringUtils.equals(Const.EXCEL_CONTENT_TYPE, file.getContentType()))
            throw new CustomException(ErrorCode.INVALID_EXCEL_UPLOAD_FILE);
    }

    /* get upload field list */
    private static <T> List<Field> getUploadFields(Class<T> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ExcelUploadColumn.class))
                .sorted((field1, field2) -> {
                    int index1 = field1.getAnnotation(ExcelUploadColumn.class).colIndex();
                    int index2 = field2.getAnnotation(ExcelUploadColumn.class).colIndex();
                    return index1 - index2;
                })
                .collect(Collectors.toList());
    }

    /* extract list from excel */
    private static <T> List<T> extractListFromExcel(Class<T> clazz) {
        List<T> list = new ArrayList<>();
        rowIndex = ROW_START_INDEX;
        for (int i=0; i < rowCount; i++) {
            row = sheet.getRow(rowIndex++);
            if (row != null)
                list.add(extractObjectFromRow(clazz));
        }
        return list;
    }

    /* extract object from row */
    private static <T> T extractObjectFromRow(Class<T> clazz) {
        try {
            T object = clazz.getDeclaredConstructor().newInstance();
            colIndex = COLUMN_START_INDEX;
            for (Field field : fields) {
                field.setAccessible(true);
                cell = row.getCell(colIndex);
                validateCellValue(field);
                field.set(object, getCellValue(field));
                colIndex++;
            }
            return object;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.FAIL_EXCEL_UPLOAD);
        }
    }

    /* validate cell value */
    private static void validateCellValue(Field field) {
        ExcelUploadColumn excelUploadColumn = field.getAnnotation(ExcelUploadColumn.class);

        //1. 필수 항목 체크
        if (excelUploadColumn.required() && (cell == null || StringUtils.isBlank(cell.toString())))
            throw new CustomException(ErrorCode.ERROR_EXCEL_UPLOAD_BY_TEMPLATE, CellReference.convertNumToColString(colIndex) + rowIndex, ErrorMessage.ExcelUpload.EMPTY_REQUIRED);

        //2. regex 체크
        String regex = excelUploadColumn.validationRegex();
        if (StringUtils.isNotBlank(regex)) {
            String value = getCellValue(field).toString();
            if (StringUtils.isNotBlank(value) && !value.matches(regex)) {
                throw new CustomException(ErrorCode.ERROR_EXCEL_UPLOAD_BY_TEMPLATE, CellReference.convertNumToColString(colIndex) + rowIndex, excelUploadColumn.errorMessage());
            }
        }
    }

    /* get cell value */
    private static Object getCellValue(Field field) {
        Class<?> type = field.getType();
        try {
            if (isIntegerType(type)) {
                return cell == null ? 0 : Math.round(cell.getNumericCellValue());
            }
            if (isFloatType(type)) {
                return cell == null ? 0 : cell.getNumericCellValue();
            }
            if (isDateType(type)) {
                return cell.getLocalDateTimeCellValue();
            }
        } catch (IllegalStateException | NumberFormatException e) {
            throw new CustomException(ErrorCode.ERROR_EXCEL_UPLOAD_BY_TEMPLATE, CellReference.convertNumToColString(colIndex) + rowIndex, ErrorMessage.ExcelUpload.INVALID_DATA_FORMAT);
        }

        return cell == null ? "" : cell.getStringCellValue();
    }

    /* check integer type */
    private static boolean isIntegerType(Class<?> type) {
        List<Class<?>> integerTypes = Arrays.asList(
                Byte.class, byte.class, Short.class, short.class, Integer.class, int.class, Long.class, long.class
        );
        return integerTypes.contains(type);
    }

    /* check float type */
    private static boolean isFloatType(Class<?> type) {
        List<Class<?>> floatTypes = Arrays.asList(
                Float.class, float.class, Double.class, double.class
        );
        return floatTypes.contains(type);
    }

    /* check date type */
    private static boolean isDateType(Class<?> type) {
        List<Class<?>> dateTypes = Arrays.asList(
                LocalDate.class, LocalDateTime.class, Date.class
        );
        return dateTypes.contains(type);
    }

}
