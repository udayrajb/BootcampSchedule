package com.eq.lcoe.bootcamp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScheduleEntry {
    private List<String> contiguousDays;
    private String event;
    private int duration;
    private String person;

    public ScheduleEntry() {
        this.contiguousDays = new ArrayList<String>();
    }

    public ScheduleEntry(List<String> contiguousDays, String event, int duration, String person) {
        Objects.requireNonNull(contiguousDays, "Contiguous days cannot be null");
        Objects.requireNonNull(event, "Event cannot be null");
        if (duration < 1) {
            throw new IllegalArgumentException("Invalid duration of the event");
        }

        this.contiguousDays = contiguousDays;
        this.event = event;
        this.duration = duration;
        this.person = person;
    }

    public List<String> getContiguousDays() {
        return contiguousDays;
    }

    public void addContiguousDay(String day) {
        this.contiguousDays.add(day);
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("{").append("\"event\":\"").append(event).append("\",\"duration\":\"")
                .append(duration).append("\",\"person\":\"").append(person).append("\",\"dates\" : [");
        for (String day: contiguousDays) {
            builder.append("\"").append(day).append("\",");
        }
        builder.delete(builder.lastIndexOf(","),builder.length());
        builder.append("]}\n");
        return builder.toString();
    }
}
