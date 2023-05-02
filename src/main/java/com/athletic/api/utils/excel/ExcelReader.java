package com.athletic.api.utils.excel;

import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.exception.ErrorMessage;
import com.athletic.api.utils.constant.Const;
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
        if (file == null) {
            throw new CustomException(ErrorCode.EMPTY_EXCEL_UPLOAD_FILE);
        }

        if (!StringUtils.equals(Const.EXCEL_CONTENT_TYPE, file.getContentType())) {
            throw new CustomException(ErrorCode.INVALID_EXCEL_UPLOAD_FILE);
        }
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
            if (row != null) {
                list.add(extractObjectFromRow(clazz));
            }
        }
        return list;
    }

    /* extract object from row */
    private static <T> T extractObjectFromRow(Class<T> clazz) {
        try {
            T object = clazz.getDeclaredConstructor().newInstance();
            for (Field field : fields) {
                colIndex = getColIndex(field);
                field.setAccessible(true);
                cell = row.getCell(colIndex);
                validateCellValue(field);
                field.set(object, getCellValue(field));
            }
            return object;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new CustomException(ErrorCode.FAIL_EXCEL_UPLOAD);
        }
    }

    /* get colIndex */
    private static int getColIndex(Field field) {
        ExcelUploadColumn excelUploadColumn = field.getAnnotation(ExcelUploadColumn.class);
        return excelUploadColumn.colIndex();
    }

    /* validate cell value */
    private static void validateCellValue(Field field) {
        ExcelUploadColumn excelUploadColumn = field.getAnnotation(ExcelUploadColumn.class);

        //1. 필수 항목 체크
        if (excelUploadColumn.required() && (cell == null || StringUtils.isBlank(cell.toString()))) {
            throw new CustomException(ErrorCode.ERROR_EXCEL_UPLOAD_BY_TEMPLATE, CellReference.convertNumToColString(colIndex) + rowIndex, ErrorMessage.ExcelUpload.EMPTY_REQUIRED);
        }

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
            if (ExcelUtil.isIntegerType(type)) {
                return cell == null ? 0 : Math.round(cell.getNumericCellValue());
            }
            if (ExcelUtil.isFloatType(type)) {
                return cell == null ? 0 : cell.getNumericCellValue();
            }
            if (ExcelUtil.isLocalDateType(type)) {
                return cell == null ? LocalDate.now() : cell.getLocalDateTimeCellValue().toLocalDate();
            }
            if (ExcelUtil.isLocalDateTimeType(type)) {
                return cell == null ? LocalDateTime.now() : cell.getLocalDateTimeCellValue();
            }
        } catch (IllegalStateException | NumberFormatException e) {
            throw new CustomException(ErrorCode.ERROR_EXCEL_UPLOAD_BY_TEMPLATE, CellReference.convertNumToColString(colIndex) + rowIndex, ErrorMessage.ExcelUpload.INVALID_DATA_FORMAT);
        }

        return cell == null ? "" : cell.getStringCellValue();
    }
}
