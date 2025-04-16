package com.ticktock.model;

import com.ticktock.model.category.DefaultSessionCategory;
import com.ticktock.model.category.SessionTagging;
import com.ticktock.controller.TimerManager;
import com.ticktock.controller.BreakManager;
import com.ticktock.model.duration.SessionDuration;
import com.ticktock.model.breaks.BreakSummary;
import com.ticktock.model.duration.SessionDurationEnum;

public class Session {
    private TimerManager timerManager;
    private BreakManager breakManager;
    private SessionDuration sessionDuration; // Total session duration in minutes
    private SessionTagging sessionTagging; // Now manages both module and categories

    /**
     * Constructor for a session with default categories.
     *
     * @param sessionDuration Total session duration in minutes.
     * @param moduleName      The module the session belongs to.
     * @param categories      One or more predefined categories.
     */
    public Session(SessionDuration sessionDuration, String moduleName, DefaultSessionCategory... categories) {
        this.sessionDuration = sessionDuration;
        this.timerManager = new TimerManager(sessionDuration);
        this.breakManager = new BreakManager();

        // Initialize with the first category
        if (categories.length > 0) {
            this.sessionTagging = new SessionTagging(moduleName, categories[0]);
            // Add remaining categories if any
            for (int i = 1; i < categories.length; i++) {
                this.sessionTagging.addCategory(categories[i]);
            }
        } else {
            throw new IllegalArgumentException("At least one category must be provided.");
        }
    }

    /**
     * Constructor for a session with a custom category.
     *
     * @param sessionDuration Total session duration in minutes.
     * @param moduleName      The module the session belongs to.
     * @param customCategory  Custom user-defined category.
     */
    public Session(SessionDuration sessionDuration, String moduleName, String customCategory) {
        this.sessionDuration = sessionDuration;
        this.timerManager = new TimerManager(sessionDuration);
        this.breakManager = new BreakManager();
        this.sessionTagging = new SessionTagging(moduleName, customCategory);
    }

    public Session(SessionDurationEnum sessionDurationEnum, String moduleName, DefaultSessionCategory... categories) {
        this(new SessionDuration(sessionDurationEnum), moduleName, categories);
    }

    public void startSession() {
        System.out.println("Session started for [" + sessionTagging.getModuleName() + "] in categories " + sessionTagging.getCategories());
        timerManager.start();
    }

    public void pauseSession() {
        timerManager.pause();
        System.out.println("Session paused.");
    }

    public void resumeSession() {
        timerManager.resume();
        System.out.println("Session resumed.");
    }

    public void startBreak() {
        if (!timerManager.isPaused()) {
            pauseSession(); // Pause session before taking a break
        }
        breakManager.startBreak();
    }

    public void stopBreak() {
        breakManager.stopBreak();
        System.out.println("Break ended. Resuming session...");
        resumeSession();
    }

    public long getRemainingSessionTime() {
        return timerManager.getRemainingTime();
    }

    public long getTotalBreakTime() {
        return breakManager.getTotalBreakTime();
    }

    public BreakManager getBreakManager() {
        return breakManager;
    }

    public TimerManager getTimerManager() {
        return timerManager;
    }

    public boolean isOnBreak() {
        return breakManager.isOnBreak();
    }

    public boolean isSessionPaused() {
        return timerManager.isPaused();
    }

    public SessionTagging getSessionTagging() {
        return sessionTagging;
    }

    public SessionDuration getSessionDuration() {
        return sessionDuration;
    }

    public BreakSummary getBreakSummary() {
        return breakManager.getBreakSummary();
    }
}

