package com.ticktock.service;

import java.util.List;

/**
 * Stores summary results of session into SessionRecord
 */
public class SessionService {
    public static void createAndSaveSession(String module, String category, int goalInMinutes, String actual, String totalBreakTime, List<String> breakDurations, String saveFileName) {

        SessionRecord record = new SessionRecord(module, category, goalInMinutes, actual, totalBreakTime, breakDurations);

        List<SessionRecord> sessions = StorageService.loadSessions(saveFileName);
        sessions.add(record);
        StorageService.saveSessions(sessions, saveFileName);
    }
}
