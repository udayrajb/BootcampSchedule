package com.eq.lcoe.bootcamp.domain;

import com.eq.lcoe.bootcamp.services.ExcelHolidaySource;
import com.eq.lcoe.bootcamp.services.HolidayService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScheduleFormatter {

    private ScheduleConfiguration configuration;
    private List<ScheduleEntry> formattedSchedule;
    private LocalDate currentDate;

    private HolidayService holidayService;

    public ScheduleFormatter(ScheduleConfiguration configuration) throws IOException, InvalidFormatException {
        Objects.requireNonNull(configuration, "Schedule cannot be null");
        this.configuration = configuration;
        this.formattedSchedule = new ArrayList<ScheduleEntry>();
        this.holidayService = new HolidayService(new ExcelHolidaySource("E:/flat-schedule-v2.xlsx"));
        this.currentDate = configuration.getStartDate();
    }

    /**
     * Iterate over the schedule entries and get the business days for an event and build the formatted schedule.
     *
     * @return List<ScheduleEntry>
     */
    public List<ScheduleEntry> getSchedule() {
        if (formattedSchedule.size() == 0) {
            makeFormattedSchedule();
        }
        return formattedSchedule;
    }

    private void makeFormattedSchedule() {
        for (String[] row : configuration.getSequence()) {
            int duration = Integer.parseInt(row[2]);
            getBusinessDaysWithWeekendAndHolidays(row[0], row[1], duration);
        }
    }

    /**
     * Get a formatted business day schedule for an event including weekend and holiday entries
     *
     * @param event
     * @param duration
     * @param person
     * @return
     */
    private void getBusinessDaysWithWeekendAndHolidays(String event, String person, int duration) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        Objects.requireNonNull(event, "Event cannot be null");

        if (duration < 1) {
            throw new IllegalArgumentException("Invalid duration of the event");
        }

        ScheduleEntry entry = new ScheduleEntry();
        entry.setEvent(event);
        entry.setPerson(person);
        entry.setDuration(duration);

        int calendarDays = duration;

        formattedSchedule.add(entry);

        for (int i = 0; i < duration; i++) {

            entry.addContiguousDay(currentDate.format(formatter));
            currentDate = currentDate.plusDays(1);
            String holiday = holidayService.getHoliday(currentDate);

            if (null != holiday) {
                entry.setDuration(i + 1);
                entry = new ScheduleEntry();
                formattedSchedule.add(entry);
                entry.setEvent(holiday);
                entry.setDuration(1);
                entry.addContiguousDay(currentDate.format(formatter));
                currentDate = currentDate.plusDays(1);
                calendarDays++;
            } else {
                if (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
                    entry.setDuration(i + 1);
                    entry = new ScheduleEntry();
                    formattedSchedule.add(entry);
                    entry.setDuration(2);
                    calendarDays +=2;
                    entry.setEvent("Weekend");
                    entry.addContiguousDay(currentDate.format(formatter));
                    currentDate = currentDate.plusDays(1);
                    entry.addContiguousDay(currentDate.format(formatter));
                    currentDate = currentDate.plusDays(1);
                }
            }
            String lastEvent = entry.getEvent();
            if (!lastEvent.equals(event)) {
                entry = new ScheduleEntry();
                formattedSchedule.add(entry);
                entry.setEvent(event);
                entry.setPerson(person);
                entry.setDuration(calendarDays-duration);
            }
        }
    }
}
