package com.ticktock.model;

import java.util.*;

public class SessionCategory {
    private Set<DefaultSessionCategory> sessionCategories;

    /**
     * Constructor to tage sessionCategories to the Session
     * @param sessionCategory DefaultSessionCategory object
     */
    public SessionCategory(DefaultSessionCategory... sessionCategory) {
        sessionCategories = new HashSet<>();
        sessionCategories.addAll(Arrays.asList(sessionCategory));
    }

    /**
     * Returns true if sessionCategory is added to the set
     * If sessionCategory object exists in the set, it returns false
     * @param sessionCategory DefaultSessionCategory object
     * @return true if sessionCategory is added to the set
     */
    public boolean addSessionCategory(DefaultSessionCategory sessionCategory) {
        return sessionCategories.add(sessionCategory);
    }

    /**
     * Returns true if specified DefaultSessionCategory exists
     * @param sessionCategory DefaultSessionCategory object
     * @return true if DefaultSessionCategory object exists
     */
    public boolean removeSessionCategory(DefaultSessionCategory sessionCategory) {
        return sessionCategories.remove(sessionCategory);
    }
}
