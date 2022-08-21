package com.arek;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private Label wordLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wordLabel.setText(String.valueOf(DatabaseManager.getWordsNumber()));
    }

}