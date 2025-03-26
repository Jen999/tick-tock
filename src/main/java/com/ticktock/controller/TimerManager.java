package com.ticktock.controller;

import com.ticktock.model.duration.SessionDuration;

import java.util.Timer;
import java.util.TimerTask;

public class TimerManager {
    private Timer timer;
    private SessionDuration duration; // Total duration in ms
    private boolean isPaused;
    private long startTime; // Stores when the timer started
    private static final int REFRESH_PERIOD = 1000;

    public TimerManager(SessionDuration sessionDuration) {
        this.duration = sessionDuration;
        this.isPaused = false;
    }

    public void start() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        startTime = System.currentTimeMillis(); // Get start time

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                duration.reduceTimeFromTimer(REFRESH_PERIOD);

                if (duration.getDurationLeft().toSeconds() <= 0) {
                    timer.cancel(); // Stop when time runs out
                    System.out.println("Time's up!");
                } else {
                    System.out.println("Time left: " + duration.getDurationLeftAsString());
                }
            }
        }, 0, TimerManager.REFRESH_PERIOD); // Runs every 1 second
    }

    public void pause() {
        if (!isPaused) {
            isPaused = true;
            timer.cancel();
        }
    }

    public void resume() {
        if (isPaused) {
            isPaused = false;
            timer = new Timer(); // Create a new timer
            startTime = System.currentTimeMillis(); // Get start time

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    duration.reduceTimeFromTimer(TimerManager.REFRESH_PERIOD);
                    if (duration.getDurationLeft().toSeconds() <= 0) {
                        timer.cancel();
                        System.out.println("Time's up!");
                    } else {
                        startTime = System.currentTimeMillis();
                        System.out.println("Time left: " + duration.getDurationLeftAsString());
                    }
                }
            }, 0, TimerManager.REFRESH_PERIOD);
        }
    }

    public long getRemainingTime() {
        return duration.getDurationLeft().toSeconds();
    }

    public boolean isPaused() {
        return isPaused;
    }
}
