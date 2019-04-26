package com.eq.lcoe.bootcamp.main;

import com.eq.lcoe.bootcamp.domain.ScheduleConfiguration;
import com.eq.lcoe.bootcamp.domain.ScheduleEntry;
import com.eq.lcoe.bootcamp.domain.ScheduleFormatter;
import com.eq.lcoe.bootcamp.services.ExcelScheduleSequenceSource;
import com.eq.lcoe.bootcamp.services.ScheduleSequenceSource;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        LocalDate startDate = LocalDate.parse("01-Jul-2019", formatter);

        ScheduleSequenceSource scheduleSequenceSource = new ExcelScheduleSequenceSource("E:/flat-schedule-v2.xlsx");

        ScheduleConfiguration scheduleConfiguration = new ScheduleConfiguration("Bootcamp 2019", startDate, scheduleSequenceSource.getSchedule());

        ScheduleFormatter scheduleFormatter = new ScheduleFormatter(scheduleConfiguration);
        List<ScheduleEntry> formattedSchedule =  scheduleFormatter.getSchedule();

        System.out.println(formattedSchedule);

    }
}
