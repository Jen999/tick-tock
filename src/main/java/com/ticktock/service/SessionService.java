package com.ticktock.service;

import java.util.List;

/**
 * Stores summary results of session into SessionRecord
 */
public class SessionService {
    public static void createAndSaveSession(String module, String category, int goalInMinutes, String actual, String breakTime) {

        SessionRecord record = new SessionRecord(module, category, goalInMinutes, actual, breakTime);

        List<SessionRecord> sessions = StorageService.loadSessions();
        sessions.add(record);
        StorageService.saveSessions(sessions);
    }
}
