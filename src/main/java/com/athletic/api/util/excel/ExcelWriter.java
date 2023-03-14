package com.athletic.api.util.excel;

import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.util.constant.Const;
import com.athletic.api.util.excel.style.ExcelCellStyle;
import com.athletic.api.util.excel.style.align.ExcelAlign;
import com.athletic.api.util.excel.style.border.ExcelBorder;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelWriter {

    private static final float DEFAULT_ROW_HEIGHT = 20;

    private static final String FLOAT_FORMAT = "#,##0.00";
    private static final String INTEGER_FORMAT = "#,##0";
    private static final String DEFAULT_FORMAT = "";

    private static final String FILE_EXTENSION = ".xlsx";
    private static final String DEFAULT_FILENAME = "Excel Download";
    private static final String DEFAULT_SHEETNAME = "Sheet1";

    private static Workbook workbook;
    private static Sheet sheet;
    private static CellStyle cellStyle;
    private static List<Field> excelFields;

    private static final int ROW_START_INDEX = 1;
    private static final int COLUMN_START_INDEX = 1;
    private static int rowIndex;

    public static void write(List<?> list, Class<?> clazz) {
        write(DEFAULT_FILENAME, DEFAULT_SHEETNAME, list, clazz);
    }

    public static void write(String filename, List<?> list, Class<?> clazz) {
        write(filename, DEFAULT_SHEETNAME, list, clazz);
    }

    public static void write(String filename, String sheetname, List<?> list, Class<?> clazz) {
        validateData(list);
        initialize(sheetname, clazz);
        renderHeader();
        renderBody(list);
        writeToResponse(filename);
    }


    /* check list size */
    private static void validateData(List<?> list) {
        int maxRows = SpreadsheetVersion.EXCEL2007.getMaxRows();
        if (list.size() > maxRows)
            throw new CustomException(ErrorCode.OVERFLOW_MAX_ROWS_EXCEL_DOWNLOAD, NumberFormat.getInstance().format(maxRows));
    }

    /* initialize excel */
    private static void initialize(String sheetname, Class<?> clazz) {
        //create workbook
        workbook = new XSSFWorkbook();
        //create sheet
        sheet = workbook.createSheet(sheetname);
        //get fields
        excelFields = getExcelFields(clazz);
        //set default row height
        sheet.setDefaultRowHeightInPoints(DEFAULT_ROW_HEIGHT);
    }

    /* get excel fields by clazz */
    private static List<Field> getExcelFields(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ExcelColumn.class))
                .sorted((field1, field2) -> {
                    int sort1 = field1.getAnnotation(ExcelColumn.class).sort();
                    int sort2 = field2.getAnnotation(ExcelColumn.class).sort();
                    return sort1 - sort2;
                })
                .collect(Collectors.toList());
    }

    /* render header row */
    private static void renderHeader() {
        rowIndex = ROW_START_INDEX;
        Row row = sheet.createRow(rowIndex++);
        int columnIndex = COLUMN_START_INDEX;

        for (Field field : excelFields) {
            ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
            setColumnWidth(columnIndex, excelColumn.width());

            Cell cell = row.createCell(columnIndex++);
            cell.setCellValue(excelColumn.headerName());

            setHeaderCellStyle(excelColumn.headerStyle());
            cell.setCellStyle(cellStyle);
        }
    }

    /* set column width */
    private static void setColumnWidth(int columnIndex, int width) {
        sheet.setColumnWidth(columnIndex, width * 256);
    }

    /* set header cell style */
    private static void setHeaderCellStyle(ExcelCellStyle headerCellStyle) {
        setCellStyle(headerCellStyle);
        setHeaderFont();
    }

    /* set cell style */
    private static void setCellStyle(ExcelCellStyle style) {
        cellStyle = workbook.createCellStyle();

        //set align
        ExcelAlign align = style.getAlign();
        cellStyle.setAlignment(align.getHorizontalAlignment());
        cellStyle.setVerticalAlignment(align.getVerticalAlignment());

        //set foreground color and pattern
        cellStyle.setFillForegroundColor(style.getColor().getColorIndex());
        cellStyle.setFillPattern(style.getFillPattern());

        //set border
        ExcelBorder border = style.getBorder();
        cellStyle.setBorderTop(border.getBorderTop());
        cellStyle.setBorderRight(border.getBorderRight());
        cellStyle.setBorderBottom(border.getBorderBottom());
        cellStyle.setBorderLeft(border.getBorderLeft());
    }

    /* set header font */
    private static void setHeaderFont() {
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
    }

    /* render body rows */
    private static void renderBody(List<?> list) {
        for (Object data : list) {
            renderBodyRow(data);
        }
    }

    /* render body row */
    private static void renderBodyRow(Object data) {
        Row row = sheet.createRow(rowIndex++);
        int columnIndex = COLUMN_START_INDEX;

        for (Field field : excelFields) {
            ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
            try {
                field.setAccessible(true);

                Cell cell = row.createCell(columnIndex++);
                setCellValue(cell, field.get(data));

                setBodyCellStyle(excelColumn, field.getType());
                cell.setCellStyle(cellStyle);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new CustomException(ErrorCode.FAIL_EXCEL_DOWNLOAD);
            }
        }
    }

    /* set cell value */
    private static void setCellValue(Cell cell, Object cellValue) {
        if (cellValue instanceof Number) {
            Number number = (Number) cellValue;
            cell.setCellValue(number.doubleValue());
            return;
        }
        cell.setCellValue(cellValue == null ? "" : cellValue.toString());
    }

    /* set body cell style */
    private static void setBodyCellStyle(ExcelColumn excelColumn, Class<?> type) {
        setCellStyle(excelColumn.bodyStyle());

        //set data format
        DataFormat dataFormat = workbook.createDataFormat();
        cellStyle.setDataFormat(getDataFormat(dataFormat, type));
    }

    /* get data format */
    private static short getDataFormat(DataFormat dataFormat, Class<?> type) {
        if (isFloatType(type))
            return dataFormat.getFormat(FLOAT_FORMAT);

        if (isIntegerType(type))
            return dataFormat.getFormat(INTEGER_FORMAT);

        return dataFormat.getFormat(DEFAULT_FORMAT);
    }

    /* check float type */
    private static boolean isFloatType(Class<?> type) {
        List<Class<?>> floatTypes = Arrays.asList(
                Float.class, float.class, Double.class, double.class
        );
        return floatTypes.contains(type);
    }

    /* check integer type */
    private static boolean isIntegerType(Class<?> type) {
        List<Class<?>> integerTypes = Arrays.asList(
                Byte.class, byte.class, Short.class, short.class, Integer.class, int.class, Long.class, long.class
        );
        return integerTypes.contains(type);
    }

    /* write excel to response */
    private static void writeToResponse(String filename) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletResponse response = attributes.getResponse();

        if (response == null)
            throw new CustomException(ErrorCode.FAIL_EXCEL_DOWNLOAD);

        try (OutputStream os = response.getOutputStream()) {
            response.setContentType(Const.EXCEL_CONTENT_TYPE);
            response.setHeader("Content-Disposition", "attachment;filename=" + getEncodedFilename(filename));
            workbook.write(os);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FAIL_EXCEL_DOWNLOAD);
        }
    }

    /* get encoded filename (한글 깨짐 방지) */
    private static String getEncodedFilename(String filename) {
        return URLEncoder.encode(LocalDate.now() + " " + filename.replaceAll(".xlsx|.xls", "") + FILE_EXTENSION, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
    }
}
