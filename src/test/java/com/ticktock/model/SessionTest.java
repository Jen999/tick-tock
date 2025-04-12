package com.ticktock.model;

import com.ticktock.model.category.DefaultSessionCategory;
import com.ticktock.model.category.SessionTagging;
import com.ticktock.model.duration.SessionDuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SessionTest {
    private Session session1;
    private Session session2;

    @BeforeEach
    public void setUp() {
        SessionDuration sessionDuration = new SessionDuration(0, 20, 30);

        // First constructor, providing default categories
        session1 = new Session(sessionDuration, "cs2105", DefaultSessionCategory.STUDY, DefaultSessionCategory.PROJECT);

        // Second constructor, providing custom category
        session2 = new Session(sessionDuration, "cs2107", "writeup");
    }

    @Test
    void startSession() {
        session1.startSession();
        assertFalse(session1.isSessionPaused());
    }

    @Test
    void pauseSession() {
        session1.startSession();
        assertFalse(session1.isSessionPaused());
        session1.pauseSession();
        assertTrue(session1.isSessionPaused());
    }

    @Test
    void resumeSession() {
        session1.startSession();
        assertFalse(session1.isSessionPaused());
        session1.pauseSession();
        assertTrue(session1.isSessionPaused());
        session1.resumeSession();
        assertFalse(session1.isSessionPaused());
    }

    @Test
    void startBreak() {
        // start break when the session is ongoing
        session1.startSession();
        assertFalse(session1.isOnBreak());
        session1.startBreak();
        assertTrue(session1.isOnBreak());
        assertTrue(session1.isSessionPaused());

        // start break when the session is not ongoing
        session2.startBreak();
        assertTrue(session2.isOnBreak());
        assertTrue(session2.isSessionPaused());
    }

    @Test
    void stopBreak() {
        session1.startBreak();
        session1.stopBreak();
        assertFalse(session1.isSessionPaused());
        assertFalse(session1.isOnBreak());
    }

    @Test
    void getRemainingSessionTime() {
        assertEquals(20L * 60 + 30L, session1.getRemainingSessionTime());

        // emulate 3 seconds passing
        session1.startSession();
        session1.getTimerManager().tick();
        session1.getTimerManager().tick();
        session1.getTimerManager().tick();
        session1.pauseSession();

        assertTrue(session1.isSessionPaused());
        assertEquals(20L * 60 + 27L, session1.getRemainingSessionTime());
    }

    @Test
    void getTotalBreakTime() {
        assertEquals(0, session1.getTotalBreakTime());

        long startTime = System.currentTimeMillis();
        session1.getBreakManager().startBreak();
        long endTime = System.currentTimeMillis();
        session1.getBreakManager().stopBreak();

        assertEquals((endTime - startTime) / 1000, session1.getTotalBreakTime());

        startTime = System.currentTimeMillis();
        session2.getBreakManager().startBreak();
        sleep(1500);
        endTime = System.currentTimeMillis();
        session2.getBreakManager().stopBreak();

        assertEquals((endTime - startTime) / 1000, session2.getTotalBreakTime());
    }

    @Test
    void getSessionTagging() {
        SessionTagging sessionTagging1 = session1.getSessionTagging();
        SessionTagging sessionTagging2 = session2.getSessionTagging();

        // Check for equality by checking module name and the categories

        // For sessionTagging1
        assertEquals("cs2105", sessionTagging1.getModuleName());
        assertTrue(sessionTagging1.getCategories().contains(DefaultSessionCategory.STUDY.toString()));
        assertTrue(sessionTagging1.getCategories().contains(DefaultSessionCategory.PROJECT.toString()));

        // For sessionTagging2
        assertEquals("cs2107", sessionTagging2.getModuleName());
        assertTrue(sessionTagging2.getCategories().contains("writeup"));
    }

    @Test
    void getSessionDuration() {
        // Duration left same as what has passed in since no reduction
        assertEquals("00:20:30", session1.getSessionDuration().getDurationLeftAsString());
        assertEquals("00:00:00", session1.getSessionDuration().getDurationPassedAsString());
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
