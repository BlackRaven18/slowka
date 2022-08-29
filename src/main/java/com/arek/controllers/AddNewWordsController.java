package com.arek.controllers;

import com.arek.database_utils.DatabaseManager;
import com.arek.database_utils.WordAndTranslation;
import com.arek.language_learning_app.Languages;
import com.arek.language_learning_app.TranslationOrder;
import com.arek.language_learning_app.WordAndTranslationsManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddNewWordsController implements Initializable {


    private WordAndTranslationsManager wordAndTranslationsManager;

    @FXML private Label messageLabel;
    @FXML private TableView<WordAndTranslation> wordsAndTranslationsTable;
    @FXML private TableColumn<WordAndTranslation, String> wordsColumn;
    @FXML private TableColumn<WordAndTranslation, String> translationsColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wordAndTranslationsManager = new WordAndTranslationsManager(Languages.SPANISH, TranslationOrder.NORMAL, new Label());

        messageLabel.setText("");

        initiateWordsAndTranslationsTableView();
    }

    private void initiateWordsAndTranslationsTableView(){
        wordsAndTranslationsTable.getItems().clear();
        ArrayList<WordAndTranslation> wordAndTranslationList = DatabaseManager.getWordsAndTranslationsAsList(TranslationOrder.NORMAL);

        wordsColumn.setCellValueFactory(new PropertyValueFactory<>("word"));
        translationsColumn.setCellValueFactory(new PropertyValueFactory<>("translation"));

        for(WordAndTranslation element : wordAndTranslationList){
            wordsAndTranslationsTable.getItems().add(element);
        }
    }
}
