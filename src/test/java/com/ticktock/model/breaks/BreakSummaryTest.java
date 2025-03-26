package com.ticktock.model.breaks;

import com.ticktock.model.duration.SessionDuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Set up should be creating multiple Breaks
 * Then add them to a list and then mock the return for breakManager
 */

public class BreakSummaryTest {
    private static BreakSummary breakSummary;
    private static List<SessionDuration> sessionDurations;
    private static long totalBreakTime;

    @BeforeAll
    public static void setup() {
        // creating a list of 3 break durations
        sessionDurations = new ArrayList<>();
        long numberOfSeconds = 0;

        SessionDuration sessionDuration;
        for (int i = 1; i <= 4; i++) {
            sessionDuration = switch (i) {
                case 1 -> new SessionDuration(i);
                case 2 -> new SessionDuration(0, 0, i);
                case 3 -> new SessionDuration(0, i, 0);
                default -> new SessionDuration(i, 0, 0);
            };

            numberOfSeconds += switch (i) {
                case 1 -> i;
                case 2 -> i;
                case 3 -> i * 60;
                default -> i * 60 * 60;
            };

            sessionDurations.add(sessionDuration);
        }

        totalBreakTime = numberOfSeconds;
        breakSummary = new BreakSummary(totalBreakTime, sessionDurations);
    }

    @Test
    public void getTotalBreakTimeInSeconds() {
        assertEquals(totalBreakTime, breakSummary.getTotalBreakTimeInSeconds());
    }

    @Test
    public void getBreakDurations() {
        assertEquals(sessionDurations, breakSummary.getBreakDurations());
    }
}
