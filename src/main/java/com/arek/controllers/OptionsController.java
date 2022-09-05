package com.arek.controllers;

import com.arek.clock_utils.ClockManager;
import com.arek.language_learning_app.AppOptions;
import com.arek.language_learning_app.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionsController implements Initializable {

    AppOptions options;

    @FXML private TextField hoursField, minutesField, secondsField;
    @FXML private TextField prefAppWidthField, prefAppHeightField, minAppWidthField, minAppHeightField;
    @FXML private Label messageLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        options = AppOptions.getInstance();

        setMessageLabel(Color.BLACK, "");

        hoursField.setText(String.valueOf(options.getClockTime().getHours()));
        minutesField.setText((String.valueOf(options.getClockTime().getMinutes())));
        secondsField.setText((String.valueOf(options.getClockTime().getSeconds())));

        prefAppWidthField.setText((String.valueOf(options.getPrefAppWidth())));
        prefAppHeightField.setText((String.valueOf(options.getPrefAppHeight())));

        minAppWidthField.setText((String.valueOf(options.getMinAppWidth())));
        minAppHeightField.setText((String.valueOf(options.getMinAppHeight())));

    }

    @FXML
    public void saveOptions() {

        if(!checkIsFieldsAreNotEmpty()){
            setMessageLabel(Color.RED, "Żadne pole nie może być puste!!!");
            return;
        }

        options.setPrefAppWidth(Integer.parseInt(prefAppWidthField.getText()));
        options.setPrefAppHeight(Integer.parseInt(prefAppHeightField.getText()));

        options.setMinAppWidth(Integer.parseInt(minAppWidthField.getText()));
        options.setMinAppHeight(Integer.parseInt(minAppHeightField.getText()));

        options.setClockHours(Integer.parseInt(hoursField.getText()));
        options.setClockMinutes(Integer.parseInt(minutesField.getText()));
        options.setClockSeconds(Integer.parseInt(secondsField.getText()));

        options.saveOptionsToFile();

        updateApp();

        setMessageLabel(Color.GREEN, "Zmienono ustawienia!");
    }

    private void updateApp(){
        Stage stage = Main.getMainStage();

        int frameExtraWidth = 14;
        int frameExtraHeight = 38;

        stage.setWidth(options.getPrefAppWidth() + frameExtraWidth);
        stage.setHeight(options.getPrefAppHeight() + frameExtraHeight);
        stage.setMinWidth(options.getMinAppWidth() + frameExtraWidth);
        stage.setMinHeight(options.getMinAppHeight() + frameExtraHeight);
    }

    private boolean checkIsFieldsAreNotEmpty(){
        if(hoursField.getText().isEmpty() || minutesField.getText().isEmpty() || secondsField.getText().isEmpty()
                || prefAppWidthField.getText().isEmpty() || prefAppHeightField.getText().isEmpty()
                || minAppWidthField.getText().isEmpty() || minAppHeightField.getText().isEmpty()){
            return false;
        }
        return true;
    }

    private void setMessageLabel(Color color, String text){
        messageLabel.setTextFill(color);
        messageLabel.setText(text);
    }
}
