package com.arek.controllers;

import com.arek.language_learning_app.AppOptions;
import com.arek.language_learning_app.Main;
import com.arek.language_learning_app.TextFieldFormatConfigurator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionsTabController implements Initializable {

    AppOptions options;

    @FXML private TextField hoursField, minutesField, secondsField;
    @FXML private TextField prefAppWidthField, prefAppHeightField, minAppWidthField, minAppHeightField;
    @FXML private Label messageLabel;

    private int hours, minutes, seconds, prefWidth, prefHeight, minWidth, minHeight;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        options = AppOptions.getInstance();

        setMessageLabel(Color.BLACK, "");

        configureTextFields();

        hoursField.setText(String.valueOf(options.getClockTime().getHours()));
        minutesField.setText((String.valueOf(options.getClockTime().getMinutes())));
        secondsField.setText((String.valueOf(options.getClockTime().getSeconds())));

        prefAppWidthField.setText((String.valueOf(options.getPrefAppWidth())));
        prefAppHeightField.setText((String.valueOf(options.getPrefAppHeight())));

        minAppWidthField.setText((String.valueOf(options.getMinAppWidth())));
        minAppHeightField.setText((String.valueOf(options.getMinAppHeight())));

        readFromTextFields();
    }

    private void configureTextFields(){
        TextFieldFormatConfigurator configurator = new TextFieldFormatConfigurator();
        configurator.configureNumericTextField(hoursField, TextFieldFormatConfigurator.intFormat);
        configurator.configureNumericTextField(minutesField, TextFieldFormatConfigurator.intFormat);
        configurator.configureNumericTextField(secondsField, TextFieldFormatConfigurator.intFormat);
        configurator.configureNumericTextField(prefAppWidthField, TextFieldFormatConfigurator.intFormat);
        configurator.configureNumericTextField(prefAppHeightField, TextFieldFormatConfigurator.intFormat);
        configurator.configureNumericTextField(minAppWidthField, TextFieldFormatConfigurator.intFormat);
        configurator.configureNumericTextField(minAppHeightField, TextFieldFormatConfigurator.intFormat);
    }

    private void readFromTextFields(){
        hours = Integer.parseInt(hoursField.getText());
        minutes = Integer.parseInt(minutesField.getText());
        seconds = Integer.parseInt(secondsField.getText());

        prefWidth = Integer.parseInt(prefAppWidthField.getText());
        prefHeight = Integer.parseInt(prefAppHeightField.getText());

        minWidth = Integer.parseInt(minAppWidthField.getText());
        minHeight = Integer.parseInt(minAppHeightField.getText());
    }

    @FXML
    public void saveOptions() {

        if(!checkIsFieldsAreNotEmpty()){
            setMessageLabel(Color.RED, "Żadne pole nie może być puste!!!");
            return;
        }

        checkIfOptionsAreCorrect();

        options.setPrefAppWidth(prefWidth);
        options.setPrefAppHeight(prefHeight);

        options.setMinAppWidth(minWidth);
        options.setMinAppHeight(minHeight);

        options.setClockHours(hours);
        options.setClockMinutes(minutes);
        options.setClockSeconds(seconds);

        options.saveOptionsToFile();

        updateApp();

        setMessageLabel(Color.GREEN, "Zmienono ustawienia!");
    }

    private void checkIfOptionsAreCorrect(){

        readFromTextFields();

        if(prefWidth < 300){ prefAppWidthField.setText("300");}
        if(prefHeight < 250){ prefAppHeightField.setText("250");}
        if(minWidth < 300){minAppWidthField.setText("300");}
        if(minHeight < 250){ minAppHeightField.setText("250");}
        if(hours > 60){ hoursField.setText("60");}
        if(minutes > 60){ minutesField.setText("60");}
        if(seconds > 60) {secondsField.setText("60");}
        if(hours <= 0 && minutes <= 0 && seconds <= 0){
            hoursField.setText("0");
            minutesField.setText("0");
            secondsField.setText("1");
        }
        readFromTextFields();
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
