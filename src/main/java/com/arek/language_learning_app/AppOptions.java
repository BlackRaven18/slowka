package com.arek.language_learning_app;

import javafx.scene.image.Image;

import java.io.File;


@SuppressWarnings("unused")
public final class AppOptions {

    private static AppOptions instance;

    public final String APP_TITLE = "Słówka";
    public final Image APP_ICON = new Image("file:icon.png");
    public final File APP_TRAY_ICON = new File("icon.png");

    private int clockTime = 20;

    private int prefAppWidth = 720;
    private int prefAppHeight = 480;

    private int minAppWidth = 600;
    private int minAppHeight = 400;

    private AppOptions(){

    }

    public static AppOptions getInstance(){
        if(instance == null){
            instance = new AppOptions();
        }

        return instance;
    }

    public int getClockTime() {
        return clockTime;
    }

    public void setClockTime(int clockTime) {
        this.clockTime = clockTime;
    }

    public int getPrefAppWidth() {
        return prefAppWidth;
    }

    public void setPrefAppWidth(int prefAppWidth) {
        this.prefAppWidth = prefAppWidth;
    }

    public int getPrefAppHeight() {
        return prefAppHeight;
    }

    public void setPrefAppHeight(int prefAppHeight) {
        this.prefAppHeight = prefAppHeight;
    }

    public int getMinAppWidth() {
        return minAppWidth;
    }

    public void setMinAppWidth(int minAppWidth) {
        this.minAppWidth = minAppWidth;
    }

    public int getMinAppHeight() {
        return minAppHeight;
    }

    public void setMinAppHeight(int minAppHeight) {
        this.minAppHeight = minAppHeight;
    }
}
