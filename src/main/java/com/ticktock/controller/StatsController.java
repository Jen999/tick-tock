package com.ticktock.controller;

import com.ticktock.service.SessionRecord;
import com.ticktock.service.StorageService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the displaying of statistics UI by loading session info from StorageService
 * Displays overall total study time, break time, and in
 */
public class StatsController {

    @FXML private Label totalSessionsLabel;
    @FXML private Label totalBreakTimeLabel;
    @FXML private Label totalStudyTimeLabel;
    @FXML private Label averageStudyTimeLabel;
    @FXML private Label averageBreakTimeLabel;
    @FXML private Label percentageOfStudyTimeLabel;
    @FXML private Button backButton;

    private static final Logger LOGGER = Logger.getLogger(StatsController.class.getName());

    @FXML
    private void handleBackButton() {
        try {
            // Load the main page (main.fxml) when back button is clicked
            Parent mainPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/main.fxml")));
            Scene mainScene = new Scene(mainPage, 500, 700);
            Stage currentStage = (Stage) backButton.getScene().getWindow(); // Get the current window
            currentStage.setScene(mainScene); // Switch to the main scene
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load stats page", e);
        }
    }

    public void initialize() {
        // Load session records from the file
        List<SessionRecord> sessions = StorageService.loadSessions(StorageService.getFileName());

        // Update statistics labels
        updateStats(sessions);
    }

    private void updateStats(List<SessionRecord> sessions) {
        // Display total number of sessions
        totalSessionsLabel.setText("Total Sessions: " + sessions.size());

        // Calculate total study time
        long totalStudyTimeSeconds = 0;
        for (SessionRecord session : sessions) {
            totalStudyTimeSeconds += parseTime(session.getActualTime());
        }

        long averageStudyTime = sessions.isEmpty() ? 0 : totalStudyTimeSeconds / sessions.size();

        // Calculate total break time and average break time
        long totalBreakTimeSeconds = 0;
        for (SessionRecord session : sessions) {
            totalBreakTimeSeconds += parseTime(session.getTotalBreakTime());
        }

        long averageBreakTime = sessions.isEmpty() ? 0 : totalBreakTimeSeconds / sessions.size();

        // Calculate percentage of total time spent studying
        double percentageOfTimeStudying = (double) (100 * totalStudyTimeSeconds) / (totalStudyTimeSeconds + totalBreakTimeSeconds);
        percentageOfTimeStudying = Math.round(percentageOfTimeStudying * 100.0) / 100.0;

        // Display total study time
        totalStudyTimeLabel.setText("Total Study Time: " + formatTime(totalStudyTimeSeconds));

        // Display average study time per session
        averageStudyTimeLabel.setText("Average Study Time: " + formatTime(averageStudyTime));

        // Display total break time
        totalBreakTimeLabel.setText("Total Break Time: " + formatTime(totalBreakTimeSeconds));

        // Display average break time per session
        averageBreakTimeLabel.setText("Average Break Time: " + formatTime(averageBreakTime));

        // Display percentage of time spent studying
        percentageOfStudyTimeLabel.setText("% of time spent studying: " + percentageOfTimeStudying + "%");
    }

    private long parseTime(String time) {
        // Convert time in "HH:mm:ss" format to seconds
        String[] parts = time.split(":");
        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);
        long seconds = Long.parseLong(parts[2]);
        return hours * 3600 + minutes * 60 + seconds;
    }

    private String formatTime(long totalSeconds) {
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
