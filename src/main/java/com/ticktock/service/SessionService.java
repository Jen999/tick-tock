package com.ticktock.service;

import com.ticktock.model.Session;
import java.util.List;

public class SessionService {
    /*
    Handles data storage, retrieval, and processing for sessions and statistics
    Data and business logic
     */

    // Create a session and pass it to StorageService for saving
    public static void createAndSaveSession(String module, String category, int goal, String actual, String breakTime) {
        System.out.println("Saving session: " + module + " | " + category + " | Goal: " + goal + " min | Actual: " + actual + " | Break: " + breakTime);

        Session newSession = new Session(module, category, goal);
        newSession.setActual(actual);
        newSession.setBreakTime(breakTime);

        List<Session> sessions = StorageService.loadSessions();
        sessions.add(newSession);
        StorageService.saveSessions(sessions);
    }
}
