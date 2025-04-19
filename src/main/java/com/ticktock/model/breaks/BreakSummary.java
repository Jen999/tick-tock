package com.ticktock.model.breaks;

import com.ticktock.model.duration.SessionDuration;

import java.util.List;

/**
 * Class that encapsulates the totalBreakTime and all the SessionDuration objects for the breaks
 */
public class BreakSummary {
    private final long totalBreakTimeInSeconds;
    private final List<SessionDuration> breakDurations;

    public BreakSummary(long total, List<SessionDuration> durations) {
        this.totalBreakTimeInSeconds = total;
        this.breakDurations = durations;
    }

    public long getTotalBreakTimeInSeconds() {
        return totalBreakTimeInSeconds;
    }

    public List<SessionDuration> getBreakDurations() {
        return breakDurations;
    }
}

