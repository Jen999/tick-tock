package com.ticktock.controller;

import com.ticktock.model.Session;
import com.ticktock.model.duration.DefaultDuration;
import com.ticktock.model.duration.SessionDuration;
import com.ticktock.service.SessionService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.List;
import java.util.Objects;

/**
 * Manages
 */
public class MainController {

    @FXML private TextField moduleField, categoryField;
    @FXML private ComboBox<DefaultDuration> durationDropdown;
    @FXML private Label timerLabel, breakTimeLabel;
    @FXML private Button startButton, breakButton, endButton;
    @FXML private ImageView hourglassImage;

    private Session currentSession;
    private Timeline uiUpdater;

    /**
     * Initialize set up when FXML is loaded by TickTockApp
     */
    public void initialize() {
        hourglassImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/hourglass.png")).toExternalForm()));
        setupDurationOptions();

        startButton.setOnAction(e -> handleStart());
        breakButton.setOnAction(e -> handleBreakToggle());
        endButton.setOnAction(e -> handleEnd());
    }

    /**
     * Set up duration dropdowns from DefaultDuration
     */
    private void setupDurationOptions() {
        durationDropdown.getItems().addAll(DefaultDuration.values());
        durationDropdown.setPromptText("Select Duration");

        // Display readable duration text in list instead of enum variable name
        durationDropdown.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(DefaultDuration item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });
        // Display readable duration text when selected instead of enum variable name
        durationDropdown.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(DefaultDuration item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });
    }

    /**
     * Set up session when module, category, and duration is filled
     */
    private void handleStart() {
        String module = moduleField.getText().trim();
        String category = categoryField.getText().trim();
        DefaultDuration selectedDuration = durationDropdown.getValue();

        if (module.isEmpty() || category.isEmpty() || selectedDuration == null) {
            showAlert("Please fill in all fields: Module, Category, and Duration.");
            return;
        }

        SessionDuration sessionDuration = new SessionDuration(selectedDuration);
        currentSession = new Session(sessionDuration, module, category);
        currentSession.startSession();

        startUIUpdater();
    }

    /**
     * Handle starting and stopping of break sessions
     */
    private void handleBreakToggle() {
        if (currentSession == null) {
            showAlert("Start a session first.");
            return;
        }

        if (!currentSession.isOnBreak()) {
            currentSession.startBreak();
            breakButton.setText("Resume");
        } else {
            currentSession.stopBreak();
            breakButton.setText("Break");
        }
    }

    /**
     * Handle ending session
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

        SessionService.createAndSaveSession(module, category, goalInMinutes, actual, totalBreakTime, breakDurations);

        showAlert("Session saved!");
        resetUI();
    }

    private void startUIUpdater() {
        if (uiUpdater != null) uiUpdater.stop();

        uiUpdater = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (currentSession != null) {
                timerLabel.setText(currentSession.getSessionDuration().getDurationLeftAsString());
                breakTimeLabel.setText("Break Time: " + formatSeconds(getCurrentBreakTime()));
            }
        }));
        uiUpdater.setCycleCount(Timeline.INDEFINITE);
        uiUpdater.play();
    }

    private void stopUIUpdater() {
        if (uiUpdater != null) {
            uiUpdater.stop();
            uiUpdater = null;
        }
    }

    private void resetUI() {
        moduleField.clear();
        categoryField.clear();
        durationDropdown.getSelectionModel().clearSelection();
        timerLabel.setText("00:00:00");
        breakTimeLabel.setText("Break Time: 00:00:00");
        breakButton.setText("Break");
        currentSession = null;
    }

    private long getCurrentBreakTime() {
        if (currentSession == null) return 0;

        long total = currentSession.getTotalBreakTime();
        if (currentSession.isOnBreak()) {
            // Add live break time if currently on break
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
