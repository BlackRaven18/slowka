package com.arek.language_learning_app;

import com.arek.clock_utils.ClockManager;
import com.dustinredmond.fxtrayicon.FXTrayIcon;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/arek/main-dashboard-view.fxml"));
        AppOptions options = AppOptions.getInstance();


        Scene scene = new Scene(fxmlLoader.load(),options.getPrefAppWidth(), options.getPrefAppHeight());

        //TrayIcon

        FXTrayIcon trayIcon = new FXTrayIcon.Builder(stage, options.APP_TRAY_ICON)
                .menuItem("Pokaż", e -> { stage.show();})
                .addExitMenuItem("Zakończ")
                .show()
                .build();


        stage.setTitle(options.APP_TITLE);
        stage.setWidth(options.getPrefAppWidth() + AppOptions.FRAME_EXTRA_WIDTH);
        stage.setHeight(options.getPrefAppHeight() + AppOptions.FRAME_EXTRA_HEIGHT);
        stage.setMinWidth(options.getMinAppWidth());
        stage.setMinHeight(options.getMinAppHeight());
        stage.getIcons().add(options.APP_ICON);
        stage.setScene(scene);
        stage.show();

//        //changing setting when app is resized
//        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->
//                System.out.println("Height: " + stage.getHeight() + " Width: " + stage.getWidth());
//
//        stage.widthProperty().addListener(stageSizeListener);
//        stage.heightProperty().addListener(stageSizeListener);


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

    public static Stage getMainStage() {
        return mainStage;
    }
}