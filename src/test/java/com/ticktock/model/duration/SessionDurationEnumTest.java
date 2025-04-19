package com.ticktock.model.duration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionDurationEnumTest {
    private static SessionDurationEnum durationFifteenMinutes;
    private static SessionDurationEnum durationThirtyMinutes;
    private static SessionDurationEnum durationFourtyFiveMinutes;
    private static SessionDurationEnum durationOneHour;
    private static SessionDurationEnum durationTwoHours;
    private static SessionDurationEnum durationThreeHours;

    @BeforeAll
    public static void setUp() {
        durationFifteenMinutes = SessionDurationEnum.FIFTEEN_MINUTES;
        durationThirtyMinutes = SessionDurationEnum.THIRTY_MINUTES;
        durationFourtyFiveMinutes = SessionDurationEnum.FORTY_FIVE_MINUTES;
        durationOneHour = SessionDurationEnum.ONE_HOUR;
        durationTwoHours = SessionDurationEnum.TWO_HOURS;
        durationThreeHours = SessionDurationEnum.THREE_HOURS;
    }

    @Test
    public void getNumberOfSeconds() {
        assertEquals(15L * 60L, durationFifteenMinutes.getNumberOfSeconds());
        assertEquals(30L * 60L, durationThirtyMinutes.getNumberOfSeconds());
        assertEquals(45L * 60L, durationFourtyFiveMinutes.getNumberOfSeconds());
        assertEquals(60L * 60L, durationOneHour.getNumberOfSeconds());
        assertEquals(2L * 60L * 60L, durationTwoHours.getNumberOfSeconds());
        assertEquals(3L * 60L * 60L, durationThreeHours.getNumberOfSeconds());
    }

    @Test
    public void toStringTest() {
        assertEquals("15 minutes", durationFifteenMinutes.toString());
        assertEquals("30 minutes", durationThirtyMinutes.toString());
        assertEquals("45 minutes", durationFourtyFiveMinutes.toString());
        assertEquals("1 hours", durationOneHour.toString());
        assertEquals("2 hours", durationTwoHours.toString());
        assertEquals("3 hours", durationThreeHours.toString());
    }
}
