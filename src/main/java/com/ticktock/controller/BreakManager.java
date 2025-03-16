package com.ticktock.controller;

import java.util.ArrayList;
import java.util.List;

public class BreakManager {
    private boolean onBreak;
    private long breakStartTime;
    private List<Long> breakDurations; // Stores all break durations

    public BreakManager() {
        this.onBreak = false;
        this.breakDurations = new ArrayList<>();
    }
    // Start a break session
    public void startBreak() {
        if (!onBreak) {
            onBreak = true;
            breakStartTime = System.currentTimeMillis(); // Record start time
            System.out.println("Break started.");
        } else {
            System.out.println("Already on break.");
        }
    }

    // Stop the current break and store the duration
    public void stopBreak() {
        if (onBreak) {
            onBreak = false;
            long breakEndTime = System.currentTimeMillis();
            long breakDuration = breakEndTime - breakStartTime; // Calculate duration
            breakDurations.add(breakDuration);
            System.out.println("Break ended. Duration: " + (breakDuration) + " ms.");
        } else {
            System.out.println("No active break to stop.");
        }
    }

    public long getTotalBreakTime() {
        long totalBreakTime = 0;
        for (long duration : breakDurations) {
            totalBreakTime += duration;
        }
        return totalBreakTime;
    }

    public List<Long> getBreakDurations() {
        return new ArrayList<>(breakDurations); // Return a copy to prevent modification
    }

    public boolean isOnBreak() {
        return onBreak;
    }
}

