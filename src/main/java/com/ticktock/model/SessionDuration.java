package com.ticktock.model;

import java.time.Duration;

public class SessionDuration {
    private Duration duration;

    /**
     * Constructor
     * @param hours Number of hours for the session
     * @param minutes Number of minutes for the session (0-59)
     * @param seconds Number of seconds for the session (0-59)
     */
    public SessionDuration(int hours, int minutes, int seconds) {
        assert hours >= 0 && minutes >= 0 && seconds >= 0;
        duration = Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
    }

    /**
     * Construct a SessionDuration object from the DefaultDuration enum
     * @param duration DefaultDuration enum
     */
    public SessionDuration(DefaultDuration duration) {
        this.duration = Duration.ofSeconds(duration.getNumberOfSeconds());
    }

    private Duration getDuration() {
        return duration;
    }

    public void minusSeconds(long seconds) {
        duration = getDuration().minusSeconds(seconds);
    }

    public void plusSeconds(long seconds) {
        duration = getDuration().plusSeconds(seconds);
    }

    public long getMinutes() {
        return getDuration().toMinutes();
    }

    public long getSeconds() {
        return getDuration().toSeconds();
    }

    public long getHours() {
        return getDuration().toHours();
    }

    /**
     * Static method to convert hours to seconds
     * @param hours Number of hours
     * @return Number of hours in seconds
     */
    public static long getNumberOfSecondsFromHours(int hours) {
        return 3600L * hours;
    }

    /**
     * Static method to convert minutes to seconds
     * @param minutes Number of minutes
     * @return Number of minutes in seconds
     */
    public static long getNumberOfSecondsFromMinutes(int minutes) {
        return 60L * minutes;
    }

    /**
     * Returns a String of the duration in HH:MM:SS
     * @return String representation of the duration object
     */
    public String toString() {
        long numberOfSeconds = getSeconds() % 60;
        long numberOfMinutes = getSeconds() / 60 % 60;
        long numberOfHours = getSeconds() / (60 * 60);
        return String.format("%02d:%02d:%02d", numberOfHours, numberOfMinutes, numberOfSeconds);
    }
}
