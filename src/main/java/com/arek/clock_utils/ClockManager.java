package com.arek.clock_utils;

import com.arek.language_learning_app.AppOptions;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("FieldCanBeLocal")

public class ClockManager {

    private static ClockManager instance;

    private final AppOptions appOptions;

    private boolean isClockRunning;

    private Button startButton, stopButton;

    private final long DELAY = 1000;
    private final long PERIOD = 1000;
    private final long HIDE_WINDOW_DELAY = 100;
    private long SHOW_WINDOW_DELAY;

    private Time time;

    private TimerTask showRemainingTime;
    private TimerTask showWindow;
    private TimerTask hideWindow;

    private Timer showRemainingTimeTimer;
    private Timer showWindowTimer;
    private Timer hideWindowTimer;

    private Label clockLabel;
    private Stage stage;

    private ClockManager(){
        appOptions = AppOptions.getInstance();

        time = appOptions.getClockTime();

        SHOW_WINDOW_DELAY = 1000 * time.getTimeConvertedToSeconds();

        isClockRunning = false;
        showRemainingTimeTimer = new Timer();
        showWindowTimer = new Timer();
        hideWindowTimer = new Timer();
    }

    public static ClockManager getInstance(){
        if(instance == null){
            instance = new ClockManager();
        }

        return instance;
    }

    public void prepareClock(Label clockLabel, Button startButton, Button stopButton){
        this.clockLabel = clockLabel;
        this.startButton = startButton;
        this.stopButton = stopButton;

        setClockTime();
    }

    private void manageButtons(){
        if(isClockRunning){
            startButton.setDisable(true);
            stopButton.setDisable(false);
        } else {
            startButton.setDisable(false);
            stopButton.setDisable(true);
        }
    }

    public void startShowingRemainingTime(){

        showRemainingTime = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() ->{
                    updateTimer();
                    setClockTime();
                });
            }
        };
        showRemainingTimeTimer.scheduleAtFixedRate(showRemainingTime, DELAY, PERIOD);
    }

    private void updateTimer(){
        if(time.getTimeConvertedToSeconds() == 1){
            showRemainingTimeTimer.cancel();
        }
         time.updateTimeAfterOneSecond();
    }

    public void startShowingWindowPeriodically(){
        showWindow = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() ->{
                    // showing window from being minimized
                    stage.show();
                    stage.setIconified(false);
                });
            }
        };
        showWindowTimer.schedule(showWindow, SHOW_WINDOW_DELAY);
    }

    public void startHidingWindow(){
        hideWindow = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() ->{
                    // hiding window
                    stage.hide();
                    //stage.setIconified(true);
                });
            }
        };

        hideWindowTimer.schedule(hideWindow, HIDE_WINDOW_DELAY);
    }



    public void startClock(){
        isClockRunning = true;

        resetTime();

        SHOW_WINDOW_DELAY = 1000 * time.getTimeConvertedToSeconds();

        manageButtons();

        resetClockTime();
        setClockTime();

        getNewTimers();
        startShowingRemainingTime();
        startShowingWindowPeriodically();
        startHidingWindow();
    }

    public void stopClock(){
        isClockRunning = false;

        resetTime();
        SHOW_WINDOW_DELAY = 1000 * time.getTimeConvertedToSeconds();


        manageButtons();

        resetClockTime();
        setClockTime();

        stopTimers();
    }

    private void resetTime(){
        time = appOptions.getClockTime();
    }

    public void setClockTime(){
        String timeFormat = String.format("%02d:%02d:%02d", time.getHours(), time.getMinutes(), time.getSeconds());
        clockLabel.setText(timeFormat);
    }

    public void resetClockTime(){
        time = appOptions.getClockTime();
    }

    private void getNewTimers(){
        stopTimers();
        showRemainingTimeTimer = new Timer();
        showWindowTimer = new Timer();
        hideWindowTimer = new Timer();
    }

    private void stopTimers(){
        showRemainingTimeTimer.cancel();
        showWindowTimer.cancel();
        hideWindowTimer.cancel();
    }

    public Label getClockLabel() {
        return clockLabel;
    }

    public void setClockLabel(Label clockLabel) {
        this.clockLabel = clockLabel;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean isClockRunning() {
        return isClockRunning;
    }
}
