package com.arek.controllers;

import com.arek.language_learning_app.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class MainDashboardController implements Initializable {


    private WordAndTranslationsManager wordAndTranslationsManager;
    private Languages language;
    private TranslationOrder translationOrder;
    private ClockManager clockManager;

    @FXML private AddNewWordsController addNewWordsController;
    @FXML private MergeDatabasesController mergeDatabasesController;

    @FXML private Label wordLabel, messageLabel, translationOrderLabel, clockLabel;
    @FXML private TextField translationField;
    @FXML private MenuButton selectLanguageMenu;
    @FXML private Button startClockButton, stopClockButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        language = Languages.SPANISH;
        translationOrder = TranslationOrder.NORMAL;
        wordAndTranslationsManager = new WordAndTranslationsManager(language, translationOrder);

        loadWordLabel();
        updateTranslationOrderLabel();

        messageLabel.setText("");

        clockManager = ClockManager.getInstance();
        clockManager.prepareClock(clockLabel, startClockButton, stopClockButton);
    }



    private void loadWordLabel(){
        wordLabel.setText(wordAndTranslationsManager.getRandomWord());
    }

    private void updateTranslationOrderLabel(){
        switch(language){
            case SPANISH:
                if(translationOrder == TranslationOrder.NORMAL) translationOrderLabel.setText("Hiszpańsko - Polski");
                else translationOrderLabel.setText("Polsko - Hiszpański");
                break;
            case ENGLISH:
                if(translationOrder == TranslationOrder.NORMAL) translationOrderLabel.setText("Angielsko - Polski");
                else translationOrderLabel.setText("Polsko - Angielski");
                break;
        }
    }

    private void updateSelectLanguageMenu(){
        switch (wordAndTranslationsManager.getSelectedLanguage()){
            case SPANISH:
                selectLanguageMenu.setText("Hiszpański");
                break;
            case ENGLISH:
                selectLanguageMenu.setText("Angielski");
                break;
        }
    }

    @FXML
    public void checkIfCorrect(){

        if(translationField.getText().isEmpty()){
            setMessageLabel(Color.BLACK, "");
            return;
        }

        if(wordAndTranslationsManager.checkTranslation(wordLabel.getText(), translationField.getText())){
            setMessageLabel(Color.GREEN, "OK!!!");
            translationField.setText("");
            loadWordLabel();

            if(clockManager.isClockRunning()){
                startClock();
            }

        } else{
            setMessageLabel(Color.RED, "NIE OK!!!");
        }

        translationField.setText("");
    }

    @FXML
    public void changeTranslationOrder(){
        messageLabel.setText("");

        if(translationOrder == TranslationOrder.NORMAL){
            translationOrder = TranslationOrder.REVERSE;
            wordAndTranslationsManager.changeTranslationOrder(translationOrder);
            loadWordLabel();
        } else{
            translationOrder = TranslationOrder.NORMAL;
            wordAndTranslationsManager.changeTranslationOrder(translationOrder);
            loadWordLabel();
        }
    }

    @FXML
    public void startClock(){
        clockManager.startClock();
    }

    @FXML
    public void stopClock(){
        clockManager.stopClock();
    }

    @FXML
    public void ifEnterPressedThenCheckIfCorrect(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)){
            checkIfCorrect();
        }
    }

    @FXML
    public void onAddWordsTabChange(){
        clockManager.stopClock();
        addNewWordsController.restartTab();

    }

    @FXML
    public void onTranslationTabChange(){
        if(wordAndTranslationsManager != null) {
            wordAndTranslationsManager.selectSpanishLanguage();
            wordAndTranslationsManager.changeTranslationOrder(TranslationOrder.NORMAL);
            updateSelectLanguageMenu();
            loadWordLabel();
        }
    }

    @FXML
    public void onMergeDatabasesTabChange(){
        onAddWordsTabChange();
        onTranslationTabChange();
    }

    @FXML
    public void selectSpanishLanguage(){
        selectLanguageMenu.setText("Hiszpański");
        wordAndTranslationsManager.selectSpanishLanguage();
        loadWordLabel();
    }

    @FXML
    public void selectEnglishLanguage(){
        selectLanguageMenu.setText("Angielski");
        wordAndTranslationsManager.selectEnglishLanguage();
        loadWordLabel();
    }

    private void setMessageLabel(Color color, String text){
        messageLabel.setTextFill(color);
        messageLabel.setText(text);
    }


}

