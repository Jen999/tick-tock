package com.ticktock.model.duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionDurationTest {
    private SessionDuration sessionDurationOne;
    private SessionDuration sessionDurationTwo;

    @BeforeEach
    public void setUp() {
        sessionDurationOne = new SessionDuration(1, 30, 30);
        sessionDurationTwo = new SessionDuration(DefaultDuration.TWO_HOURS);
    }

    @Test
    public void getDurationLeftAsString() {
        assertEquals("01:30:30", sessionDurationOne.getDurationLeftAsString());
        assertEquals("02:00:00", sessionDurationTwo.getDurationLeftAsString());

        // time passes by 45 minutes
        sessionDurationOne.reduceTimeFromTimer(45 * 60 * 1000);
        sessionDurationTwo.reduceTimeFromTimer(45 * 60 * 1000);
        assertEquals("00:45:30", sessionDurationOne.getDurationLeftAsString());
        assertEquals("01:15:00", sessionDurationTwo.getDurationLeftAsString());

        // time passes by 0.5 seconds
        sessionDurationOne.reduceTimeFromTimer(500);
        sessionDurationTwo.reduceTimeFromTimer(500);
        assertEquals("00:45:29", sessionDurationOne.getDurationLeftAsString());
        assertEquals("01:14:59", sessionDurationTwo.getDurationLeftAsString());
    }

    @Test
    public void getDurationPassedAsString() {
        // before any time passes
        assertEquals("00:00:00", sessionDurationOne.getDurationPassedAsString());
        assertEquals("00:00:00", sessionDurationTwo.getDurationPassedAsString());

        // time passes by 45 minutes
        sessionDurationOne.reduceTimeFromTimer(45 * 60 * 1000);
        sessionDurationTwo.reduceTimeFromTimer(45 * 60 * 1000);
        assertEquals("00:45:00", sessionDurationOne.getDurationPassedAsString());
        assertEquals("00:45:00", sessionDurationTwo.getDurationPassedAsString());

        // time passes by 0.5 seconds
        sessionDurationOne.reduceTimeFromTimer(500);
        sessionDurationTwo.reduceTimeFromTimer(500);
        assertEquals("00:45:00", sessionDurationOne.getDurationPassedAsString());
        assertEquals("00:45:00", sessionDurationTwo.getDurationPassedAsString());
    }

    @Test
    public void getNumberOfSecondsFromHours() {
        assertEquals(2 * 60 * 60, SessionDuration.getNumberOfSecondsFromHours(2));
    }

    @Test
    public void getNumberOfSecondsFromMinutes() {
        assertEquals(62 * 60, SessionDuration.getNumberOfSecondsFromMinutes(62));
    }
}
