package com.arek;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainDashboardController implements Initializable {

    private WordAndTranslationsManager wordAndTranslationsManager;

    @FXML private Label wordLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wordAndTranslationsManager = new WordAndTranslationsManager();

        wordLabel.setText(String.valueOf(DatabaseManager.getWordsNumber()));
    }

}