package com.ticktock.controller;

import com.ticktock.service.SessionService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MainController {
    @FXML private ComboBox<String> durationDropdown;
    @FXML private TextField moduleField, categoryField;
    @FXML private Label timerLabel, breakTimeLabel;
    @FXML private Button startButton, breakButton, endButton;
    @FXML private ImageView hourglassImage;

    private Timeline timer;
    private Timeline breakTimer;
    private LocalDateTime sessionStart;
    private LocalDateTime breakStart;
    private int breakElapsedSeconds;
    private int remainingTime;
    private boolean isPaused = false;
    private long totalBreakTime = 0; // Track total break time

    public void initialize() {
        // Set initial hourglass image
        hourglassImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/hourglass.png")).toExternalForm()));

        startButton.setOnAction(e -> startSession());
        breakButton.setOnAction(e -> toggleBreak());
        endButton.setOnAction(e -> endSession());
    }

    private void startSession() {
        if (durationDropdown.getValue() == null || moduleField.getText().isEmpty() || categoryField.getText().isEmpty()) {
            showAlert("Please fill in all fields (Module, Category, Duration).");
            return;
        }

        remainingTime = getDurationInSeconds(durationDropdown.getValue());
        sessionStart = LocalDateTime.now(); // Record session start time
        totalBreakTime = 0; // Reset break tracking
        updateBreakTimeLabel(totalBreakTime);
        updateTimerLabel();

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (!isPaused && remainingTime > 0) {
                remainingTime--;
                updateTimerLabel();
                updateImageProgress();
            }
            if (remainingTime == 0) {
                endSession();
                showAlert("Study session completed!");
            }
        }));
        timer.setCycleCount(remainingTime);
        timer.play();
    }

    private void toggleBreak() {
        if (!isPaused) {
            // Start break (only update breakStart if it's a new break session)
            if (breakStart == null) {
                breakStart = LocalDateTime.now();
            }
            breakButton.setText("Resume");
            isPaused = true;

            // Start the break timer (updates the break time every second)
            breakTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                long currentBreakDuration = ChronoUnit.SECONDS.between(breakStart, LocalDateTime.now());
                updateBreakTimeLabel(currentBreakDuration + totalBreakTime); // Show total break duration live
            }));
            breakTimer.setCycleCount(Timeline.INDEFINITE);
            breakTimer.play();

        } else {
            // End break: Accumulate total break time
            long breakDuration = ChronoUnit.SECONDS.between(breakStart, LocalDateTime.now());
            totalBreakTime += breakDuration; // Accumulate in long
            breakStart = null; // Reset so the next break can start fresh

            // Stop the break timer
            if (breakTimer != null) {
                breakTimer.stop();
            }

            updateBreakTimeLabel(totalBreakTime);
            breakButton.setText("Break");
            isPaused = false;
        }
    }

    private void endSession() {
        if (timer != null) {
            timer.stop();
        }

        if (breakTimer != null) {
            breakTimer.stop();
        }

        // Ensure total break time is fully updated before saving
        if (isPaused && breakStart != null) {
            long breakDuration = ChronoUnit.SECONDS.between(breakStart, LocalDateTime.now());
            totalBreakTime += breakDuration;
            breakStart = null; // Reset for next session
        }

        // Calculate actual study time (Session Duration - Break Time)
        long elapsedSeconds = ChronoUnit.SECONDS.between(sessionStart, LocalDateTime.now());
        long actualStudySeconds = elapsedSeconds - totalBreakTime;
        if (actualStudySeconds < 0) actualStudySeconds = 0;

        // Format study and break time
        String formattedActualTime = formatTime(actualStudySeconds);
        String formattedBreakTime = formatTime(totalBreakTime);

        // Create and Save Session
        String module = moduleField.getText();
        String category = categoryField.getText();
        int goal = getDurationInSeconds(durationDropdown.getValue()) / 60; // Convert seconds to minutes

        System.out.println("Saving session: " + module + " | " + category + " | Goal: " + goal + " min | Actual: " + formattedActualTime + " | Break: " + formattedBreakTime);

        SessionService.createAndSaveSession(module, category, goal, formattedActualTime, formattedBreakTime);

        // Reset UI elements
        remainingTime = 0;
        updateTimerLabel();
        updateBreakTimeLabel(totalBreakTime); // Ensure UI reflects the correct break time
        hourglassImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/hourglass.png")).toExternalForm()));

        // Reset session state for new session
        isPaused = false;
    }

    private void updateTimerLabel() {
        timerLabel.setText(formatTime(remainingTime));
    }

    private void updateBreakTimeLabel(long totalSeconds) {
        breakTimeLabel.setText("Break Time: " + formatTime(totalSeconds));
    }

    private void updateImageProgress() {
        if (remainingTime <= 10) {
//            hourglassImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/hourglassFinishing.png")).toExternalForm()));
        } else if (remainingTime <= remainingTime / 2) {
//            hourglassImage.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/hourglassHalf.png")).toExternalForm()));
        }
    }

    private int getDurationInSeconds(String duration) {
        return switch (duration) {
            case "30 min" -> 1800;
            case "45 min" -> 2700;
            case "1 hr" -> 3600;
            case "1.5 hr" -> 5400;
            case "2 hrs" -> 7200;
            default -> 0;
        };
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setContentText(message);
        alert.show();
    }

    private String formatTime(long totalSeconds) {
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}
