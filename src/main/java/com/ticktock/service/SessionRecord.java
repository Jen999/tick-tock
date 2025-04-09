package com.ticktock.service;

import java.util.List;

/**
 * Format session to be handled by SessionService to be stored in Storage
 */
public class SessionRecord {
    private String module;
    private String category;
    private int goalMinutes;
    private String actualTime;
    private String totalBreakTime;
    private List<String> breakSessions;

    public SessionRecord(String module, String category, int goalMinutes, String actualTime, String totalBreakTime, List<String> breakSessions) {
        this.module = module;
        this.category = category;
        this.goalMinutes = goalMinutes;
        this.actualTime = actualTime;
        this.totalBreakTime = totalBreakTime;
        this.breakSessions = breakSessions;
    }

    public String getModule() {
        return module;
    }

    public String getCategory() {
        return category;
    }

    public int getGoalMinutes() {
        return goalMinutes;
    }

    public String getActualTime() {
        return actualTime;
    }

    public String getTotalBreakTime() {
        return totalBreakTime;
    }

    public List<String> getBreakSessions() {
        return breakSessions;
    }
}
