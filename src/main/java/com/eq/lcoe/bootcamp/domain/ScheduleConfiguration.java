package com.eq.lcoe.bootcamp.domain;

import java.time.LocalDate;

public final class ScheduleConfiguration {

    private String title;
    private LocalDate startDate;
    private String[][] sequence;

    public ScheduleConfiguration(String title, LocalDate startDate, String[][] sequence) {
        this.title = title;
        this.startDate = startDate;
        this.sequence = sequence;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public String[][] getSequence() {
        return sequence;
    }

}
