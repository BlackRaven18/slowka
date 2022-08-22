package com.arek;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class MainDashboardController implements Initializable {



    // ****************************************************
    // Translation Tab
    // ****************************************************
    private WordAndTranslationsManager wordAndTranslationsManager;

    @FXML private Label wordLabel, messageLabel;
    @FXML private TextField translationField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wordAndTranslationsManager = new WordAndTranslationsManager();
        loadWordLabel();
    }

    private void loadWordLabel(){

        wordLabel.setText(wordAndTranslationsManager.getRandomWord());
    }

    @FXML
    public void checkIfCorrect(){

        //if true
        if(wordAndTranslationsManager.checkTranslation(wordLabel.getText(), translationField.getText())){
            messageLabel.setText("OK!!!");
            translationField.setText("");
            loadWordLabel();
        } else{
            messageLabel.setText("NIE OK!!!");
        }

        translationField.setText("");
    }

}