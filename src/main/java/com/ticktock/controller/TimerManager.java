package com.ticktock.controller;

import java.util.Timer;
import java.util.TimerTask;

public class TimerManager {
    private Timer timer;
    private long duration; // Total duration in ms
    private long remainingTime; // Remaining time after paused
    private boolean isPaused;
    private long startTime; // Stores when the timer started

    public TimerManager(long minutes) {
        this.duration = minutes * 60 * 1000; // Convert minutes to ms
        this.remainingTime = duration;
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
                long elapsed = System.currentTimeMillis() - startTime;
                remainingTime = duration - elapsed;

                if (remainingTime <= 0) {
                    timer.cancel(); // Stop when time runs out
                    System.out.println("Time's up!");
                } else {
                    System.out.println("Time left: " + remainingTime / 1000 + " sec");
                }
            }
        }, 0, 1000); // Runs every 1 second
    }

    public void pause() {
        if (!isPaused) {
            remainingTime -= System.currentTimeMillis() - startTime; // Remaining time
            isPaused = true;
            timer.cancel(); // Stop the timer
        }
    }

    public void resume() {
        if (isPaused) {
            isPaused = false;
            startTime = System.currentTimeMillis(); // Reset start time
            timer = new Timer(); // Create a new timer

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    long elapsed = System.currentTimeMillis() - startTime;
                    remainingTime -= elapsed;
                    if (remainingTime <= 0) {
                        timer.cancel();
                        System.out.println("Time's up!");
                    } else {
                        System.out.println("Time left: " + remainingTime / 1000 + " sec");
                    }
                }
            }, 0, 1000);
        }
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    public boolean isPaused() {
        return isPaused;
    }
}
