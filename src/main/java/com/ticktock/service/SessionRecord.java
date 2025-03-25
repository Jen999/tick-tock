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
}
