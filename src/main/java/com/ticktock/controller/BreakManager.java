package com.ticktock.controller;

import com.ticktock.model.breaks.BreakSummary;
import com.ticktock.model.duration.SessionDuration;

import java.util.ArrayList;
import java.util.List;

/**
 * BreakManager manages all the break durations for the current session
 * Each session object has a break manager
 */
public class BreakManager {
    private boolean onBreak;
    private long breakStartTime;
    private List<SessionDuration> breakDurations; // Stores all break durations
    private SessionDuration currentBreakSession = null;

    /**
     * Constructor
     */
    public BreakManager() {
        this.onBreak = false;
        this.breakDurations = new ArrayList<>();
    }

    /**
     * Starts a break if not on break else do nothing
     */
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

    /**
     * Stops the current break and stores it, else do nothing
     */
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

    /**
     * Get the total break time for being tracked by BreakManager
     * @return The total break time for the current session, truncating the decimal point (Example: 1500 milliseconds
     * returns 1 second)
     */
    public long getTotalBreakTime() {
        long totalBreakTime = 0;
        for (SessionDuration duration : breakDurations) {
            totalBreakTime += duration.getDurationPassed().toMillis();
        }
        return totalBreakTime / 1000;
    }

    /**
     * Returns a copy of the list of breakDurations in BreakManager
     * @return A copy of breakDurations to prevent modification
     */
    public List<SessionDuration> getBreakDurations() {
        return new ArrayList<>(breakDurations);
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

