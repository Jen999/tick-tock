package com.ticktock.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Session {
    private String module;      // Module name
    private String category;    // Study category
    private int goal;           // Goal duration (selected time in minutes)
    private String actual;         // Completed duration (in minutes)
    private String breakTime;      // Total break time (in minutes)
    private String timestamp;   // When session started

    public Session(String module, String category, int goal) {
        this.module = module;
        this.category = category;
        this.goal = goal;
        this.actual = "00:00:00";
        this.breakTime = "00:00:00";
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Getters
    public String getModule() { return module; }
    public String getCategory() { return category; }
    public int getGoal() { return goal; }
    public String getActual() { return actual; }
    public String getBreakTime() { return breakTime; }
    public String getTimestamp() { return timestamp; }

    // Setters
    public void setActual(String actual) { this.actual = actual; }
    public void setBreakTime(String breakTime) { this.breakTime = breakTime; }

    @Override
    public String toString() {
        return module + " | " + category + " | Goal: " + goal + " min | Actual: " + actual +
                " min | Break: " + breakTime + " min | " + timestamp;
    }
}
