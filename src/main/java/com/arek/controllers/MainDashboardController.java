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
    public void onTranslationTabChange() {
        translationTabController.resetTab();
        AppOptions.getInstance().clearLastFocusedTextFieldAndCaretPosition();
    }

    @FXML
    public void onAddWordsTabChange(){
        addNewWordsTabController.resetTab();
        AppOptions.getInstance().clearLastFocusedTextFieldAndCaretPosition();
    }

    @FXML
    public void onMergeDatabasesTabChange(){
        mergeDatabasesTabController.resetTab();
    }

    @FXML
    public void onOptionsTabChange(){
    }



}

