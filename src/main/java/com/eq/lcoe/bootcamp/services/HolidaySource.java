package com.eq.lcoe.bootcamp.services;

import java.time.LocalDate;

public interface HolidaySource {
    public String getHoliday(LocalDate date);
}
