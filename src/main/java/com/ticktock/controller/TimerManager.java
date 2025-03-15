package com.ticktock.service;

public class TimerManager {
    private long duration; // Total study time in milliseconds
    private long remainingTime; // Remaining time when paused
    private long startTime;
    private boolean isPaused;

    public TimerManager(long durationInMinutes) {
        this.duration = durationInMinutes * 60 * 1000; // Convert minutes to milliseconds
        this.remainingTime = duration;
        this.isPaused = false;
    }

    public void start() {
        if (isPaused) {
            isPaused = false;
            startTime = System.currentTimeMillis(); // Resume from paused state
        } else {
            startTime = System.currentTimeMillis(); // Start a new session
        }
    }

    public void pause() {
        if (!isPaused) {
            remainingTime -= System.currentTimeMillis() - startTime; // Calculate remaining time
            isPaused = true;
        }
    }

    public long getRemainingTime() {
        if (isPaused) {
            return remainingTime;
        }
        return Math.max(0, duration - (System.currentTimeMillis() - startTime));
    }

    public boolean isPaused() {
        return isPaused;
    }
}
