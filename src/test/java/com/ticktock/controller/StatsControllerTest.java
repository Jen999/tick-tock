package com.ticktock.controller;

import com.ticktock.service.SessionRecord;
import javafx.application.Platform;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatsControllerTest {

    private StatsController statsController;

    @BeforeAll
    public static void setUpJavaFX() {
        System.setProperty("javafx.application.headless", "true");
        Platform.startup(() -> {
        }); // Initialize the JavaFX platform for background tasks
    }

    @BeforeEach
    void setUp() throws Exception {
        statsController = new StatsController();
        // Inject mock labels into the StatsController instance
        mockLabels();
    }

    private void mockLabels() throws Exception {
        setField(statsController, "totalSessionsLabel", new Label());
        setField(statsController, "totalBreakTimeLabel", new Label());
        setField(statsController, "totalStudyTimeLabel", new Label());
        setField(statsController, "averageStudyTimeLabel", new Label());
        setField(statsController, "averageBreakTimeLabel", new Label());
        setField(statsController, "percentageOfStudyTimeLabel", new Label());
    }

    private void setField(Object object, String fieldName, Object value) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    @Test
    public void testStatsAreUpdatedCorrectly_WithValidSessionData() throws Exception {
        // test data for sessions
        List<SessionRecord> sessions = Arrays.asList(
                new SessionRecord("Module1", "Category1", 60, "01:30:00", "00:30:00", Arrays.asList("Break1", "Break2")),
                new SessionRecord("Module2", "Category2", 120, "02:00:00", "00:45:00", Arrays.asList("Break3", "Break4"))
        );
        updateStats(sessions);
        // label updates happen on the JavaFX thread
        Platform.runLater(() -> {
            // Check if the labels are updated as expected
            try {
                assertEquals("Total Sessions: 2", getLabelText("totalSessionsLabel"));
                assertEquals("Total Study Time: 03:30:00", getLabelText("totalStudyTimeLabel"));
                assertEquals("Total Break Time: 00:45:00", getLabelText("totalBreakTimeLabel"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void updateStats(List<SessionRecord> sessions) throws Exception {
        // access the private method using reflection
        var updateStatsMethod = StatsController.class.getDeclaredMethod("updateStats", List.class);
        updateStatsMethod.setAccessible(true);
        updateStatsMethod.invoke(statsController, sessions);
    }

    private String getLabelText(String labelName) throws Exception {
        Field field = statsController.getClass().getDeclaredField(labelName);
        field.setAccessible(true);
        return ((Label) field.get(statsController)).getText();
    }
}


