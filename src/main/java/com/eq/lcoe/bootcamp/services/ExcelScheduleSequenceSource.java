package com.eq.lcoe.bootcamp.services;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

public class ExcelScheduleSequenceSource implements ScheduleSequenceSource {

    private String filePath;

    public ExcelScheduleSequenceSource(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String[][] getSchedule() {
        String[][] schedule = null;
        try {
            schedule = ExcelReader.readSchedule(filePath,0,1,3);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return schedule;
    }
}
