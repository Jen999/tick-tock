package com.ticktock.model.duration;

import java.time.Duration;

public class SessionDuration {
    private final Duration duration;
    private Duration durationLeft;
    private Duration durationPassed;

    /**
     * Constructor
     * @param hours Number of hours for the session
     * @param minutes Number of minutes for the session (0-59)
     * @param seconds Number of seconds for the session (0-59)
     */
    public SessionDuration(long hours, long minutes, long seconds) {
        assert hours >= 0 && minutes >= 0 && seconds >= 0;
        duration = Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
        durationLeft = Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
        durationPassed = Duration.ofSeconds(Duration.ZERO.getSeconds());
    }

    public SessionDuration(long seconds) {
        assert seconds >= 0;
        duration = Duration.ofSeconds(seconds);
        durationLeft = Duration.ZERO;
        durationPassed = Duration.ofSeconds(seconds);
    }

    /**
     * Construct a SessionDuration object from the DefaultDuration enum
     * @param duration DefaultDuration enum
     */
    public SessionDuration(SessionDurationEnum duration) {
        this.duration = Duration.ofSeconds(duration.getNumberOfSeconds());
        durationLeft = Duration.ofSeconds(duration.getNumberOfSeconds());
        durationPassed = Duration.ofSeconds(Duration.ZERO.getSeconds());
    }

    public Duration getDurationLeft() {
        return durationLeft;
    }

    public Duration getDurationPassed() {
        return durationPassed;
    }

    public Duration getDuration() {
        return duration;
    }

    public void reduceTimeFromTimer(long milliseconds) {
        durationLeft = getDurationLeft().minusMillis(milliseconds);
        durationPassed = getDurationPassed().plusMillis(milliseconds);
    }

    public void addTimeToTimer(long milliseconds) {
        durationLeft = getDurationLeft().plusMillis(milliseconds);
    }

    /**
     * Static method to convert hours to seconds
     * @param hours Number of hours
     * @return Number of hours in seconds
     */
    public static long getNumberOfSecondsFromHours(long hours) {
        return 3600L * hours;
    }

    /**
     * Static method to convert minutes to seconds
     * @param minutes Number of minutes
     * @return Number of minutes in seconds
     */
    public static long getNumberOfSecondsFromMinutes(long minutes) {
        return 60L * minutes;
    }

    /**
     * Returns a String of the duration left in HH:MM:SS
     * @return String representation of the duration object
     */
    public String getDurationLeftAsString() {
        return getDurationAsString(getDurationLeft());
    }

    public String getDurationPassedAsString() {
        return getDurationAsString(getDurationPassed());
    }

    private String getDurationAsString(Duration duration) {
        int numberOfSeconds = duration.toSecondsPart();
        int numberOfMinutes = duration.toMinutesPart();
        int numberOfHours = duration.toHoursPart();
        return String.format("%02d:%02d:%02d", numberOfHours, numberOfMinutes, numberOfSeconds);
    }

    /** Returns the corresponding SessionDurationEnum based on the current duration in seconds, or null if no match is found. */
    public SessionDurationEnum getSessionDurationEnum() {
        for (SessionDurationEnum enumValue : SessionDurationEnum.values()) {
            if (enumValue.getNumberOfSeconds() == this.duration.getSeconds()) {
                return enumValue;
            }
        }
        return null;
    }

}
