package com.arek.controllers;

import com.arek.clock_utils.ClockManager;
import com.arek.language_learning_app.AppOptions;
import com.arek.language_learning_app.TranslationOrder;
import com.arek.language_learning_app.WordAndTranslationsManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainDashboardController implements Initializable {




    @FXML private TranslationTabController translationTabController;
    @FXML private AddNewWordsTabController addNewWordsTabController;
    @FXML private MergeDatabasesTabController mergeDatabasesTabController;
    @FXML private OptionsTabController optionsTabController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    @FXML
    public void onTranslationTabChange(){
        WordAndTranslationsManager wordAndTranslationsManager = translationTabController.getWordAndTranslationsManager();
        if(wordAndTranslationsManager != null) {
            wordAndTranslationsManager.selectSpanishLanguage();
            wordAndTranslationsManager.changeTranslationOrder(TranslationOrder.NORMAL);
            translationTabController.updateSelectLanguageMenu();
            translationTabController.loadWordLabel();
            AppOptions.getInstance().clearLastFocusedTextFieldAndCaretPosition();
        }
    }

    @FXML
    public void onAddWordsTabChange(){
        ClockManager.getInstance().stopClock();
        addNewWordsTabController.restartTab();
        AppOptions.getInstance().clearLastFocusedTextFieldAndCaretPosition();
    }

    @FXML
    public void onMergeDatabasesTabChange(){
        onAddWordsTabChange();
        onTranslationTabChange();
    }

    @FXML
    public void onOptionsTabChange(){
        ClockManager.getInstance().stopClock();
    }



}

