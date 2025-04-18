package com.ticktock.controller;

import com.ticktock.model.duration.SessionDuration;

/**
 * TimerManager manages the sessionDuration for the current Session
 */
public class TimerManager {
    private SessionDuration duration; // Total duration in ms
    private boolean isPaused;

    /**
     * Constructor
     * @param sessionDuration SessionDuration object for the current Session
     */
    public TimerManager(SessionDuration sessionDuration) {
        this.duration = sessionDuration;
        this.isPaused = false;
    }

    public void start() {
        isPaused = false;
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

    /**
     * Gets the remaining time for the Session
     * @return Number of seconds left for the current session
     */
    public long getRemainingTime() {
        return duration.getDurationLeft().toSeconds();
    }

    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Reduces the duration by 1 second. This method is expected to be called every second.
     * To prevent inconsistent updates due to slight inaccuracies by taking currentTime - startTime, the
     * reduction in duration is fixed at 1 second.
     */
    public void tick() {
        if (!isPaused) {
            if (duration.getDurationLeft().getSeconds() > 0) {
                duration.reduceTimeFromTimer(1000); // 1-second tick from UI updater
            }
        }
    }
}
