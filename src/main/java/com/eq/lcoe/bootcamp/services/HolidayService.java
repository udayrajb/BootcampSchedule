package com.eq.lcoe.bootcamp.services;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.time.LocalDate;

public final class HolidayService {

    private HolidaySource source;

    public HolidayService(HolidaySource source) {
        try {
            this.source = new ExcelHolidaySource("E:/flat-schedule-v2.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    public String getHoliday(LocalDate date) {
        return source.getHoliday(date);
    }
}
