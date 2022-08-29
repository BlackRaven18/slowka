package com.arek.controllers;

import com.arek.database_utils.WordAndTranslation;
import com.arek.language_learning_app.Languages;
import com.arek.language_learning_app.TranslationOrder;
import com.arek.language_learning_app.WordAndTranslationsManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
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
        wordsColumn.setCellValueFactory(new PropertyValueFactory<>("word"));
        translationsColumn.setCellValueFactory(new PropertyValueFactory<>("translation"));

        WordAndTranslation word = new WordAndTranslation("El hombre", "mężczyzna");

        wordsAndTranslationsTable.getItems().add(new WordAndTranslation("El hombre", "mężczyzna"));
        wordsAndTranslationsTable.getItems().add(new WordAndTranslation("La nina", "girl"));
        wordsAndTranslationsTable.getItems().add(new WordAndTranslation("El hombre", "mężczyzna"));
    }
}
