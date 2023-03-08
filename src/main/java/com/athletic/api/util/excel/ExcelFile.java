package com.athletic.api.util.excel;

import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import com.athletic.api.util.excel.style.ExcelCellStyle;
import com.athletic.api.util.excel.style.align.ExcelAlign;
import com.athletic.api.util.excel.style.border.ExcelBorder;
import com.athletic.api.util.excel.style.color.ExcelColor;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelFile {

    private static final float DEFAULT_ROW_HEIGHT = 20;

    private static final String FLOAT_FORMAT = "#,##0.00";
    private static final String INTEGER_FORMAT = "#,##0";
    private static final String DEFAULT_FORMAT = "";

    private static final String EXCEL_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String FILE_EXTENSION = ".xlsx";
    private static final String DEFAULT_FILENAME = "Excel Download";

    private XSSFWorkbook workbook;
    private Sheet sheet;
    private Class<?> clazz;
    private List<?> list;
    private String filename;
    private CellStyle cellStyle;
    private List<Field> excelFields;

    private static final int ROW_START_INDEX = 1;
    private static final int COLUMN_START_INDEX = 1;
    private int rowIndex = ROW_START_INDEX;

    public ExcelFile(List<?> list, Class<?> clazz) {
        this(DEFAULT_FILENAME, list, clazz);
    }

    public ExcelFile(String filename, List<?> list, Class<?> clazz) {
        validateData(list);
        initialize(filename, list, clazz);
        renderHeader();
        renderBody();
    }

    /* check list size */
    private void validateData(List<?> list) {
        int maxRows = SpreadsheetVersion.EXCEL2007.getMaxRows();
        if (list.size() > maxRows)
            throw new CustomException(ErrorCode.OVERFLOW_MAX_ROWS_DOWNLOAD_EXCEL);
    }

    /* initialize excel */
    private void initialize(String filename, List<?> list, Class<?> clazz) {
        //create workbook
        workbook = new XSSFWorkbook();
        //create sheet
        sheet = workbook.createSheet();
        //set filename
        this.filename = LocalDate.now() + " " + filename.replaceAll(".xlsx|.xls", "");
        //set list
        this.list = list;
        //set clazz
        this.clazz = clazz;
        //get fields
        this.excelFields = getExcelFields();
        //set default row height
        sheet.setDefaultRowHeightInPoints(DEFAULT_ROW_HEIGHT);
    }

    /* get excel fields by clazz */
    private List<Field> getExcelFields() {
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
    private void renderHeader() {
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
    private void setColumnWidth(int columnIndex, int width) {
        sheet.setColumnWidth(columnIndex, width * 256);
    }

    /* set header cell style */
    private void setHeaderCellStyle(ExcelCellStyle headerCellStyle) {
        setCellStyle(headerCellStyle);
        setHeaderFont();
    }

    /* set cell style */
    private void setCellStyle(ExcelCellStyle style) {
        cellStyle = workbook.createCellStyle();

        //set align
        ExcelAlign align = style.getExcelAlign();
        cellStyle.setAlignment(align.getHorizontalAlignment());
        cellStyle.setVerticalAlignment(align.getVerticalAlignment());

        //set foreground
        ExcelColor color = style.getExcelColor();
        cellStyle.setFillForegroundColor(color.getColorIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); //추후 변경

        //set border
        ExcelBorder border = style.getExcelBorder();
        cellStyle.setBorderTop(border.getBorderTop());
        cellStyle.setBorderRight(border.getBorderRight());
        cellStyle.setBorderBottom(border.getBorderBottom());
        cellStyle.setBorderLeft(border.getBorderLeft());
    }

    /* set header font */
    private void setHeaderFont() {
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
    }

    /* render body rows */
    private void renderBody() {
        for (Object data : list) {
            renderBodyRow(data);
        }
    }

    /* render body row */
    private void renderBodyRow(Object data) {
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
            }
        }
    }

    /* set cell value */
    private void setCellValue(Cell cell, Object cellValue) {
        if (cellValue instanceof Number) {
            Number number = (Number) cellValue;
            cell.setCellValue(number.doubleValue());
            return;
        }
        cell.setCellValue(cellValue == null ? "" : cellValue.toString());
    }

    /* set body cell style */
    private void setBodyCellStyle(ExcelColumn excelColumn, Class<?> type) {
        setCellStyle(excelColumn.bodyStyle());

        //set data format
        DataFormat dataFormat = workbook.createDataFormat();
        cellStyle.setDataFormat(getDataFormat(dataFormat, type));
    }

    /* get data format */
    private short getDataFormat(DataFormat dataFormat, Class<?> type) {
        if (isFloatType(type))
            return dataFormat.getFormat(FLOAT_FORMAT);

        if (isIntegerType(type))
            return dataFormat.getFormat(INTEGER_FORMAT);

        return dataFormat.getFormat(DEFAULT_FORMAT);
    }

    /* check float type */
    private boolean isFloatType(Class<?> type) {
        List<Class<?>> floatTypes = Arrays.asList(
                Float.class, float.class, Double.class, double.class
        );
        return floatTypes.contains(type);
    }

    /* check integer type */
    private boolean isIntegerType(Class<?> type) {
        List<Class<?>> integerTypes = Arrays.asList(
                Byte.class, byte.class, Short.class, short.class, Integer.class, int.class, Long.class, long.class
        );
        return integerTypes.contains(type);
    }

    /* write excel file */
    public void write(HttpServletResponse response) {
        try {
            response.setContentType(EXCEL_CONTENT_TYPE);
            response.setHeader("Content-Disposition", "attachment;filename=" + filename + FILE_EXTENSION);
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FAIL_DOWNLOAD_EXCEL);
        }
    }

}
