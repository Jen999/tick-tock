package com.ticktock.controller;

import com.ticktock.model.SessionTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BreakManagerTest {
    private BreakManager breakManager;

    @BeforeEach
    public void setUp() {
        breakManager = new BreakManager();
    }

    @Test
    void startAndStopBreak() {
        /**
         * The order of the test goes as follows
         * 1. Check that the initialization is correct
         * 2. Check that break is started
         * 3. Check that using startBreak twice in a row does not change the onBreak behaviour
         * 4. Check that break is ended
         * 5. Check that using endBreak twice in a row does not change the onBreak behaviour
         */
        assertFalse(breakManager.isOnBreak());
        breakManager.startBreak();
        assertTrue(breakManager.isOnBreak());
        breakManager.stopBreak();
        assertFalse(breakManager.isOnBreak());
    }

    @Test
    void startBreakTwice() {
        assertFalse(breakManager.isOnBreak());
        breakManager.startBreak();
        breakManager.startBreak();
        assertTrue(breakManager.isOnBreak());
    }

    @Test
    void stopBreakTwice() {
        assertFalse(breakManager.isOnBreak());
        breakManager.startBreak();
        breakManager.stopBreak();
        breakManager.stopBreak();
        assertFalse(breakManager.isOnBreak());
    }

    @Test
    void oneBreak() {
        assertEquals(0, breakManager.getTotalBreakTime());
        breakManager.startBreak();
        sleep(1500);
        breakManager.stopBreak();
        assertEquals(1500 / 1000, breakManager.getTotalBreakTime());
    }

    @Test
    void twoBreaks() {
        assertEquals(0, breakManager.getTotalBreakTime());
        breakManager.startBreak();
        sleep(1500);
        breakManager.stopBreak();
        breakManager.startBreak();
        sleep(1500);
        breakManager.stopBreak();
        assertEquals(3000 / 1000, breakManager.getTotalBreakTime());
    }

    void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
