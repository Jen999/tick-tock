package com.ticktock.model;

import com.ticktock.model.category.DefaultSessionCategory;
import com.ticktock.model.duration.SessionDuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionTest {
    @BeforeEach
    public void setUp() {
        SessionDuration sessionDuration = new SessionDuration(0, 20, 30);

        // First constructor, providing default categories
        Session session1 = new Session(sessionDuration, "cs2105", DefaultSessionCategory.STUDY, DefaultSessionCategory.PROJECT);

        // Second constructor, providing custom category
        Session session2 = new Session(sessionDuration, "cs2107", "writeup");
    }

    @Test
    void startSession() {

    }

    @Test
    void pauseSession() {

    }

    @Test
    void resumeSession() {

    }

    @Test
    void startBreak() {

    }

    @Test
    void stopBreak() {

    }

    @Test
    void getRemainingSessionTime() {

    }

    @Test
    void getTotalBreakTime() {

    }

    @Test
    void getBreakManager() {

    }

    @Test
    void isOneBreak() {

    }

    @Test
    void isSessionPaused() {

    }

    @Test
    void getSessionTagging() {

    }

    @Test
    void getSessionDuration() {

    }

    @Test
    void getBreakSummary() {

    }
}
