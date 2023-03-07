package com.athletic.api.util.excel;

import com.athletic.api.exception.CustomException;
import com.athletic.api.exception.ErrorCode;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class ExcelFile {

    private static final String FILE_EXTENSION = ".xlsx";
    private static final String DEFAULT_FILENAME = "Downloaded Excel";

    private XSSFWorkbook workbook;
    private Sheet sheet;
    private Row row;
    private Cell cell;
    private Class<?> clazz;
    private List<?> list;
    private String filename;
    private CellStyle cellStyle;
    private Font font;

    private static final int ROW_START_INDEX = 1;
    private static final int COLUMN_START_INDEX = 1;
    private int rowIndex = ROW_START_INDEX;

    public ExcelFile(List<?> list, Class<?> clazz) {
        create(DEFAULT_FILENAME, list, clazz);
        renderHeader();
        renderBody();
    }

    public ExcelFile(String filename, List<?> list, Class<?> clazz) {
        create(filename, list, clazz);
        renderHeader();
        renderBody();
    }

    private void create(String filename, List<?> list, Class<?> clazz) {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet();
        this.list = list;
        this.clazz = clazz;
        this.filename = filename.replaceAll(".xlsx|.xls", "");
    }

    private void setColumnWidth(int columnIndex, int width) {
        sheet.setColumnWidth(columnIndex, width);
    }

    private void setHeaderCellStyle(ExcelColumn excelColumn) {
        cellStyle = workbook.createCellStyle();
        font = workbook.createFont();

        //set font
        font.setBold(true);
        cellStyle.setFont(font);

        //set align
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //set foreground
        cellStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.index);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //set border
        cellStyle.setBorderTop(BorderStyle.MEDIUM);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBorderLeft(BorderStyle.MEDIUM);
    }

    private void setBodyCellStyle(ExcelColumn excelColumn) {
        cellStyle = workbook.createCellStyle();

        cellStyle.setAlignment(excelColumn.align().getHorizontalAlignment());
        cellStyle.setVerticalAlignment(excelColumn.align().getVerticalAlignment());
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
    }

    private void renderHeader() {
        row = sheet.createRow(rowIndex++);
        int columnIndex = COLUMN_START_INDEX;


        for (Field field : clazz.getDeclaredFields()) {
            ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
            if (excelColumn != null) {
                setColumnWidth(columnIndex, excelColumn.width());

                cell = row.createCell(columnIndex++);
                cell.setCellValue(excelColumn.headerName());

                setHeaderCellStyle(excelColumn);
                cell.setCellStyle(cellStyle);
            }
        }
    }

    private void renderBody() {
        for (Object data : list) {
            row = sheet.createRow(rowIndex++);
            int columnIndex = COLUMN_START_INDEX;


            for (Field field : clazz.getDeclaredFields()) {
                ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                if (excelColumn != null) {
                    try {
                        field.setAccessible(true);

                        cell = row.createCell(columnIndex++);
                        cell.setCellValue((String) field.get(data));

                        setBodyCellStyle(excelColumn);
                        cell.setCellStyle(cellStyle);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void write(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename + FILE_EXTENSION);
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FAIL_DOWNLOAD_EXCEL);
        }
    }

}
