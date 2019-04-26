package com.eq.lcoe.bootcamp.services;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExcelHolidaySource implements HolidaySource {

    private String[][] holidays;

    public ExcelHolidaySource(String filePath) throws IOException, InvalidFormatException {
        holidays = ExcelReader.readHolidays(filePath, 1, 2, 2);
    }

    @Override
    public String getHoliday(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        String formattedDate = date.format(formatter);

        for (String[] row : holidays) {
            String dateColumn = row[0];
            if (formattedDate.equals(dateColumn)) {
                return row[1];
            }
        }

        return null;
    }
}
