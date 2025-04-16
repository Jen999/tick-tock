package com.ticktock.controller;

import com.ticktock.model.breaks.BreakSummary;
import com.ticktock.model.duration.SessionDuration;

import java.util.ArrayList;
import java.util.List;

public class BreakManager {
    private boolean onBreak;
    private long breakStartTime;
    private List<SessionDuration> breakDurations; // Stores all break durations
    private SessionDuration currentBreakSession = null;

    public BreakManager() {
        this.onBreak = false;
        this.breakDurations = new ArrayList<>();
    }

    // Start a break session
    public void startBreak() {
        if (!onBreak) {
            onBreak = true;
            breakStartTime = System.currentTimeMillis(); // Record start time
            currentBreakSession = new SessionDuration(0);
            System.out.println("Break started.");
        } else {
            System.out.println("Already on break.");
        }
    }

    // Stop the current break and store the duration
    public void stopBreak() {
        if (onBreak && currentBreakSession != null) {
            onBreak = false;
            long breakEndTime = System.currentTimeMillis();
            long breakDuration = breakEndTime - breakStartTime; // Calculate duration

            currentBreakSession.reduceTimeFromTimer(breakDuration);

            breakDurations.add(currentBreakSession);
            System.out.println("Break ended. Duration: " + currentBreakSession.toString());

            currentBreakSession = null;
        } else {
            System.out.println("No active break to stop.");
        }
    }

    public long getTotalBreakTime() {
        long totalBreakTime = 0;
        for (SessionDuration duration : breakDurations) {
            totalBreakTime += duration.getDurationPassed().toMillis();
        }
        return totalBreakTime / 1000;
    }

    public List<SessionDuration> getBreakDurations() {
        return new ArrayList<>(breakDurations); // Return a copy to prevent modification
    }

    public long getBreakStartTime() {
        return breakStartTime;
    }

    public BreakSummary getBreakSummary() {
        return new BreakSummary(getTotalBreakTime(), getBreakDurations());
    }

    public boolean isOnBreak() {
        return onBreak;
    }
}

