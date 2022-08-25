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

    private final long DELAY = 1000;
    private final long PERIOD = 1000;
    private final int CLOCK_TIME = 5;

    private TimerTask showRemainingTime;
    private TimerTask showWindow;

    private Timer showRemainingTimeTimer;
    private Timer showWindowTimer;

    private Label clockLabel;
    private Stage stage;

    private int remainingTime = CLOCK_TIME;

    private ClockManager(){
        isClockRunning = false;
        showRemainingTimeTimer = new Timer();
        showWindowTimer = new Timer();
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
                    System.out.println(time);

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
        showWindowTimer.schedule(showWindow, DELAY * remainingTime);

    }

    public void startClock(){
        isClockRunning = true;

        manageButtons();

        remainingTime = CLOCK_TIME;

        getNewTimers();
        startShowingRemainingTime();
        startShowingWindowPeriodically();
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
    }

    private void stopTimers(){
        showRemainingTimeTimer.cancel();
        showWindowTimer.cancel();
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
}
