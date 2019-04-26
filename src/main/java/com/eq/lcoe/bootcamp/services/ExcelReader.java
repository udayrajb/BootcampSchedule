package com.eq.lcoe.bootcamp.services;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

public class ExcelReader {
    public static String[][] readSchedule(String fileName, int sheetNumber, int startRow, int columnsToRead) throws IOException, InvalidFormatException {
        final InputStream fileInput = new FileInputStream(fileName);
        final Workbook workbook = WorkbookFactory.create(fileInput);
        final Sheet sheet = workbook.getSheetAt(sheetNumber);
        final int rowsToRead = sheet.getLastRowNum() - startRow + 1; // as POI rows are 0 index based
        final String[][] schedule = new String[rowsToRead][columnsToRead];
        final int NUMBER_COLUMN = 2;

        for (int i = 0; i < rowsToRead; i++) {
            int currentRow = i + startRow;
            Row row = sheet.getRow(currentRow);
            for (int j = 0; j < columnsToRead; j++) {
                Cell cell = row.getCell(j);
                if (j == NUMBER_COLUMN) {
                    schedule[i][j] = Integer.toString(((Double) cell.getNumericCellValue()).intValue());
                } else {
                    schedule[i][j] = cell.getStringCellValue();
                }
            }
        }
        return schedule;
    }

    public static String[][] readHolidays(String fileName, int sheetNumber, int startRow, int columnsToRead) throws IOException, InvalidFormatException {
        final InputStream fileInput = new FileInputStream(fileName);
        final Workbook workbook = WorkbookFactory.create(fileInput);
        final Sheet sheet = workbook.getSheetAt(sheetNumber);
        final int rowsToRead = sheet.getLastRowNum() - startRow + 1; // as POI rows are 0 index based
        final String[][] schedule = new String[rowsToRead][columnsToRead];
        final int DATE_COLUMN = 0;
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");

        for (int i = 0; i < rowsToRead; i++) {
            int currentRow = i + startRow;
            Row row = sheet.getRow(currentRow);
            for (int j = 0; j < columnsToRead; j++) {
                Cell cell = row.getCell(j);
                if (j == DATE_COLUMN) {
                    schedule[i][j] = format.format(cell.getDateCellValue());
                } else {
                    schedule[i][j] = cell.getStringCellValue();
                }
            }
        }
        return schedule;
    }
}
