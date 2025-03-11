package com.ticktock.model;

public enum DefaultDuration {
    FIFTEEN_MINUTES(SessionDuration.getNumberOfSecondsFromMinutes(15)),
    THIRTY_MINUTES(SessionDuration.getNumberOfSecondsFromMinutes(30)),
    FORTY_FIVE_MINUTES(SessionDuration.getNumberOfSecondsFromMinutes(45)),
    ONE_HOUR(SessionDuration.getNumberOfSecondsFromHours(1)),
    TWO_HOURS(SessionDuration.getNumberOfSecondsFromHours(2)),
    THREE_HOURS(SessionDuration.getNumberOfSecondsFromHours(3));

    private final long numberOfSeconds;

    DefaultDuration(long numberOfSeconds) {
        this.numberOfSeconds = numberOfSeconds;
    }

    public long getNumberOfSeconds() {
        return numberOfSeconds;
    }

    /**
     * Returns the DefaultDuration in HH:MM:SS format
     * @return String representation of the Duration object
     */
    public String toString() {
        long numberOfSeconds = getNumberOfSeconds() % 60;
        long numberOfMinutes = getNumberOfSeconds() / 60 % 60;
        long numberOfHours = getNumberOfSeconds() / (60 * 60);
        return String.format("%02d:%02d:%02d", numberOfHours, numberOfMinutes, numberOfSeconds);
    }
}
