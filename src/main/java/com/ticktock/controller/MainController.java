package com.ticktock.controller;

import com.ticktock.model.Session;
import com.ticktock.model.duration.SessionDurationEnum;
import com.ticktock.model.duration.SessionDuration;
import com.ticktock.service.SessionService;
import com.ticktock.util.SessionContext;

import com.ticktock.service.StorageService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages user interactions with main UI
 */
public class MainController {

    @FXML
    private TextField moduleField, categoryField;
    @FXML
    private ComboBox<SessionDurationEnum> durationDropdown;
    @FXML
    private Label timerLabel, breakTimeLabel;
    @FXML
    private Button sessionToggleButton, endButton;
    @FXML
    private ImageView hourglassImage;
    @FXML
    private Button statsButton;

    private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());
    private Session currentSession;
    private static Timeline uiUpdater;

    /**
     * Initialize set up when FXML is loaded by TickTockApp
     */
    public void initialize() {
        Session restored = SessionContext.getCurrentSession();
        if (restored != null) {
            currentSession = restored;
            sessionToggleButton.setText(currentSession.isOnBreak() ? "Resume" : "Break");
            sessionToggleButton.setStyle(currentSession.isOnBreak()
                    ? "-fx-background-color: #388E3C; -fx-text-fill: white; -fx-font-size: 16px;"
                    : "-fx-background-color: #FFC107; -fx-text-fill: black; -fx-font-size: 16px;");
            moduleField.setText(currentSession.getSessionTagging().getModuleName());
            categoryField.setText(currentSession.getSessionTagging().getCategories().iterator().next());
            SessionDurationEnum sessionDurationEnum = currentSession.getSessionDuration().getSessionDurationEnum(); // Assuming a method like this exists
            durationDropdown.setValue(sessionDurationEnum);
            startUIUpdater();
        }

        hourglassImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/hourglass.png")).toExternalForm()));
        setupDurationOptions();

        sessionToggleButton.setOnAction(e -> handleSessionToggle());
        endButton.setOnAction(e -> handleEnd());
        statsButton.setOnAction(e -> handleStatsPage());
    }

    /**
     * Handle stats page navigating
     */
    @FXML
    private void handleStatsPage() {
        // Load the Stats page using FXMLLoader
        // Check if the current session is on break
        if (currentSession == null || currentSession.isOnBreak()) {
            try {
                Parent statsPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/stats.fxml")));
                Scene statsScene = new Scene(statsPage, 500, 700);
                // Get current window
                Stage currentStage = (Stage) statsButton.getScene().getWindow();
                // Set new scene for stats page
                currentStage.setScene(statsScene);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to load stats page", e);
            }
        } else {
            showAlert("You can only access the stats page during break time.");
        }
    }

    /**
     * Set up duration dropdowns from DefaultDuration
     */
    private void setupDurationOptions() {
        durationDropdown.getItems().addAll(SessionDurationEnum.values());
        durationDropdown.setPromptText("Select Duration");

        // Display readable duration text in list instead of enum variable name
        durationDropdown.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(SessionDurationEnum item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });
        // Display readable duration text when selected instead of enum variable name
        durationDropdown.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(SessionDurationEnum item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });
    }

    /**
     * Handle Start-Break toggle
     * Set up session when module, category, and duration is filled and change to 'Break'
     * Toggle between 'Break' and 'Resume' subsequently, until session is ended
     */
    private void handleSessionToggle() {
        String label = sessionToggleButton.getText();

        switch (label) {
        case "Start" -> {
                /*
                Start session
                 */
            String module = moduleField.getText().trim();
            String category = categoryField.getText().trim();
            SessionDurationEnum selectedDuration = durationDropdown.getValue();

            if (module.isEmpty() || category.isEmpty() || selectedDuration == null) {
                showAlert("Please fill in all fields: Module, Category, and Duration.");
                return;
            }

            SessionDuration sessionDuration = new SessionDuration(selectedDuration);
            currentSession = new Session(sessionDuration, module, category);
            SessionContext.setCurrentSession(currentSession);
            currentSession.startSession();
            sessionToggleButton.setText("Break");
            sessionToggleButton.setStyle("-fx-background-color: #FFC107; -fx-text-fill: black; -fx-font-size: 16px;"); // yellow
            startUIUpdater();
        }
            /*
            Start break and pause session
             */
        case "Break" -> {
            if (currentSession != null && !currentSession.isOnBreak()) {
                currentSession.startBreak();
                sessionToggleButton.setText("Resume");
                sessionToggleButton.setStyle("-fx-background-color: #388E3C; -fx-text-fill: white; -fx-font-size: 16px;"); // yellow
            }
        }
            /*
            End break and resume session
             */
        case "Resume" -> {
            if (currentSession != null && currentSession.isOnBreak()) {
                currentSession.stopBreak();
                sessionToggleButton.setText("Break");
                sessionToggleButton.setStyle("-fx-background-color: #FFC107; -fx-text-fill: black; -fx-font-size: 16px;"); // yellow
            }
        }
        }
    }


    /**
     * Handle ending session and formatting to call createAndSaveSession method
     */
    private void handleEnd() {
        if (currentSession == null) {
            showAlert("No active session.");
            return;
        }

        stopUIUpdater();

        String module = currentSession.getSessionTagging().getModuleName();
        String category = currentSession.getSessionTagging().getCategories().iterator().next();
        int goalInMinutes = (int) currentSession.getSessionDuration().getDuration().toMinutes();
        String actual = currentSession.getSessionDuration().getDurationPassedAsString();

        // Get both the total break time and the break sessions
        long totalBreakSeconds = currentSession.getTotalBreakTime();
        String totalBreakTime = formatSeconds(totalBreakSeconds);
        List<String> breakDurations = currentSession.getBreakManager()
                .getBreakDurations()
                .stream()
                .map(SessionDuration::getDurationPassedAsString)
                .toList();

        SessionService.createAndSaveSession(module, category, goalInMinutes, actual, totalBreakTime, breakDurations, StorageService.getFileName());

        showAlert("Session saved!");
        resetUI();
        SessionContext.clear();

    }

    /**
     * Handles updating of timer and break stopwatch
     */
    private void startUIUpdater() {
        if (uiUpdater != null) {
            uiUpdater.stop();
            uiUpdater = null;
        }

        uiUpdater = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (currentSession != null) {
                currentSession.getTimerManager().tick(); //
                timerLabel.setText(currentSession.getSessionDuration().getDurationLeftAsString());
                breakTimeLabel.setText("Break Time: " + formatSeconds(getCurrentBreakTime()));
            }
        }));
        uiUpdater.setCycleCount(Timeline.INDEFINITE);
        uiUpdater.play();
    }

    /**
     * Stop UI Updater
     */
    private void stopUIUpdater() {
        if (uiUpdater != null) {
            uiUpdater.stop();
            uiUpdater = null;
        }
    }

    /**
     * Reset UI Updater
     */
    private void resetUI() {
        moduleField.clear();
        categoryField.clear();
        durationDropdown.getSelectionModel().clearSelection();
        timerLabel.setText("00:00:00");
        breakTimeLabel.setText("Break Time: 00:00:00");
        /*
        Set start-break toggle back to 'Start'
         */
        sessionToggleButton.setText("Start");
        sessionToggleButton.setStyle("-fx-background-color: #388E3C; -fx-text-fill: white; -fx-font-size: 16px;"); // green
        currentSession = null;
    }

    /**
     * For updating live break stopwatch on UI Updater
     */
    private long getCurrentBreakTime() {
        if (currentSession == null) return 0;

        long total = currentSession.getTotalBreakTime();
        if (currentSession.isOnBreak()) {
            long ongoingBreak = (System.currentTimeMillis() - currentSession.getBreakManager().getBreakStartTime()) / 1000;
            return total + ongoingBreak;
        }
        return total;
    }

    private String formatSeconds(long totalSeconds) {
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("TickTock");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
