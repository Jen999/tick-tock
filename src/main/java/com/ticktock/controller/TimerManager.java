package com.ticktock.controller;

import com.ticktock.model.duration.SessionDuration;

public class TimerManager {
    private SessionDuration duration; // Total duration in ms
    private boolean isPaused;

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

    public long getRemainingTime() {
        return duration.getDurationLeft().toSeconds();
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void tick() {
        if (!isPaused) {
            if (duration.getDurationLeft().getSeconds() > 0) {
                duration.reduceTimeFromTimer(1000); // 1-second tick from UI updater
            }
        }
    }
}
