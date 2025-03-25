package com.ticktock.service;

public class SessionRecord {
    private String module;
    private String category;
    private int goalMinutes;
    private String actualTime;
    private String breakTime;

    public SessionRecord(String module, String category, int goalMinutes, String actualTime, String breakTime) {
        this.module = module;
        this.category = category;
        this.goalMinutes = goalMinutes;
        this.actualTime = actualTime;
        this.breakTime = breakTime;
    }
}
