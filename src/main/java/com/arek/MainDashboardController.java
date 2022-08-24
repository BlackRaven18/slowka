package com.arek;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class MainDashboardController implements Initializable {



    // ****************************************************
    // Translation Tab
    // ****************************************************
    private WordAndTranslationsManager wordAndTranslationsManager;
    private ClockManager clockManager;

    @FXML private Label wordLabel, messageLabel, translationOrderLabel, clockLabel;
    @FXML private TextField translationField;
    @FXML private MenuButton selectLanguageMenu;
    @FXML private MenuItem spanishLanguageItem, englishLanguageItem;
    @FXML private Button startClockButton, stopClockButton;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wordAndTranslationsManager = new WordAndTranslationsManager(TranslationOrder.NORMAL);
        loadWordLabel();

        clockManager = ClockManager.getInstance();
        clockManager.setClockLabel(clockLabel);

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

    @FXML
    public void changeTranslationOrder(){

        TranslationOrder translationOrder = wordAndTranslationsManager.getTranslationOrder();

        messageLabel.setText("");

        if(translationOrder == TranslationOrder.NORMAL){
            wordAndTranslationsManager.changeTranslationOrder(TranslationOrder.REVERSE);
            loadWordLabel();
            translationOrderLabel.setText("Polsko - Hiszpański");
        } else{
            wordAndTranslationsManager.changeTranslationOrder(TranslationOrder.NORMAL);
            loadWordLabel();
            translationOrderLabel.setText("Hiszpańsko - Polski");
        }
    }

    @FXML
    public void startClock(){
        startClockButton.setDisable(true);
        stopClockButton.setDisable(false);
        clockManager.startClock();


    }

    @FXML
    public void stopClock(){
        startClockButton.setDisable(false);
        stopClockButton.setDisable(true);
        clockManager.stopClock();
    }

    @FXML
    public void ifEnterPressedThenCheckIfCorrect(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)){
            checkIfCorrect();
        }
    }


    @FXML
    public void selectSpanishLanguage(){
        selectLanguageMenu.setText("Hiszpański");
    }

    @FXML
    public void selectEnglishLanguage(){
        selectLanguageMenu.setText("Angielski");
    }
}

