package com.arek;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import com.dustinredmond.fxtrayicon.FXTrayIcon;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-dashboard-view.fxml"));

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
    }



    public static void main(String[] args) {
        launch();
    }
}