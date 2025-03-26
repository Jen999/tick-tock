package com.ticktock.service;

import com.ticktock.model.duration.SessionDuration;

import java.util.List;

/**
 * Stores summary results of session into SessionRecord
 */
public class SessionService {
    public static void createAndSaveSession(String module, String category, int goalInMinutes, String actual, String totalBreakTime, List<String> breakDurations) {

        SessionRecord record = new SessionRecord(module, category, goalInMinutes, actual, totalBreakTime, breakDurations);

        List<SessionRecord> sessions = StorageService.loadSessions();
        sessions.add(record);
        StorageService.saveSessions(sessions);
    }
}
