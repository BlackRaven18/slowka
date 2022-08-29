package com.arek.language_learning_app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/arek/main-dashboard-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 720, 480);

        //TrayIcon

//        FXTrayIcon trayIcon = new FXTrayIcon.Builder(stage, new File("icon.png"))
//                .menuItem("Show", e -> { stage.show();})
//                .addExitMenuItem("Exit")
//                .show()
//                .build();


        stage.setTitle("Słówka");
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setScene(scene);
        stage.show();

        // clock stage
        ClockManager clockManager = ClockManager.getInstance();
        clockManager.setStage(stage);

    }

    @Override
    public void stop() throws Exception {
        super.stop();

        ClockManager clockManager = ClockManager.getInstance();
        clockManager.stopClock();

    }

    public static void main(String[] args) {
        launch();
    }
}