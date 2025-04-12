package com.ticktock.util;
import com.ticktock.model.Session;

public class SessionContext {
    private static Session currentSession;

    public static Session getCurrentSession() {
        return currentSession;
    }

    public static void setCurrentSession(Session session) {
        currentSession = session;
    }

    public static void clear() {
        currentSession = null;
    }
}

