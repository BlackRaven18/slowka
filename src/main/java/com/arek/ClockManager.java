package com.arek;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.util.Timer;
import java.util.TimerTask;

public class ClockManager {

    private static ClockManager instance;

    private boolean isClockRunning;

    private Button startButton, stopButton;

    private final int CLOCK_TIME = 20;
    private final long DELAY = 1000;
    private final long PERIOD = 1000;
    private final long SHOW_WINDOW_DELAY = 1000 * CLOCK_TIME;
    private final long HIDE_WINDOW_DELAY = 100;


    private TimerTask showRemainingTime;
    private TimerTask showWindow;
    private TimerTask hideWindow;

    private Timer showRemainingTimeTimer;
    private Timer showWindowTimer;
    private Timer hideWindowTimer;

    private Label clockLabel;
    private Stage stage;

    private int remainingTime = CLOCK_TIME;

    private ClockManager(){
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
                    int time = setTime();

                    String timeFormat = String.format("00:00:%02d", time);
                    clockLabel.setText(timeFormat);
                });
            }
        };
        showRemainingTimeTimer.scheduleAtFixedRate(showRemainingTime, DELAY, PERIOD);
    }

    private int setTime(){
        if(remainingTime == 1){
            showRemainingTimeTimer.cancel();
        }

        return --remainingTime;
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
                    //stage.hide();
                    stage.setIconified(true);
                });
            }
        };

        hideWindowTimer.schedule(hideWindow, HIDE_WINDOW_DELAY);
    }



    public void startClock(){
        isClockRunning = true;

        manageButtons();

        remainingTime = CLOCK_TIME;

        getNewTimers();
        startShowingRemainingTime();
        startShowingWindowPeriodically();
        startHidingWindow();
    }

    public void stopClock(){
        isClockRunning = false;

        manageButtons();

        clockLabel.setText("00:00:00");

        stopTimers();
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
