package com.arek.controllers;

import com.arek.database_utils.DatabaseQueryManager;
import com.arek.database_utils.WordAndTranslation;
import com.arek.language_learning_app.TranslationOrder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddNewWordsController implements Initializable {


    @FXML private Label messageLabel;
    @FXML private MenuButton selectLanguageMenu;
    @FXML private TextField wordField, translationField;

    @FXML private TableView<WordAndTranslation> wordsAndTranslationsTable;
    @FXML private TableColumn<WordAndTranslation, String> wordsColumn;
    @FXML private TableColumn<WordAndTranslation, String> translationsColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        messageLabel.setText("");

        initiateWordsAndTranslationsTableView();
    }

    public void restartTab(){
        messageLabel.setText("");
        selectSpanishLanguage();
    }

    private void initiateWordsAndTranslationsTableView(){
        wordsAndTranslationsTable.getItems().clear();
        ArrayList<WordAndTranslation> wordAndTranslationList = DatabaseQueryManager.getWordsAndTranslationsAsList(TranslationOrder.NORMAL);

        wordsColumn.setCellValueFactory(new PropertyValueFactory<>("word"));
        translationsColumn.setCellValueFactory(new PropertyValueFactory<>("translation"));

        for(WordAndTranslation element : wordAndTranslationList){
            wordsAndTranslationsTable.getItems().add(element);
        }

        wordsAndTranslationsTable.refresh();
    }

    @FXML
    public void selectSpanishLanguage(){
        selectLanguageMenu.setText("Hiszpański");
        DatabaseQueryManager.changeToSpanish();
        initiateWordsAndTranslationsTableView();
    }

    @FXML
    public void selectEnglishLanguage(){
        selectLanguageMenu.setText("Angielski");
        DatabaseQueryManager.changeToEnglish();
        initiateWordsAndTranslationsTableView();
    }
    @FXML
    private void selectWordTranslationTableRow(){
        WordAndTranslation selectedWordWithTranslation = getSelectedWordWithTranslation();

        if(selectedWordWithTranslation != null) {
            wordField.setText(selectedWordWithTranslation.getWord());
            translationField.setText(selectedWordWithTranslation.getTranslation());
        }
    }

    private WordAndTranslation getSelectedWordWithTranslation(){
        int index = wordsAndTranslationsTable.getSelectionModel().getSelectedIndex();

        if(index < 0){
            return null;
        }

        return new WordAndTranslation(wordsColumn.getCellData(index), translationsColumn.getCellData(index));
    }


    @FXML
    public void addNewWord(){
        trimTextFieldsContent();

        if(!wordField.getText().isEmpty() && !translationField.getText().isEmpty()) {
            DatabaseQueryManager.addWordWithTranslation(new WordAndTranslation(wordField.getText(), translationField.getText()));
            initiateWordsAndTranslationsTableView();

            setMessageLabel(Color.GREEN, "Dodano nowe słówko");
            wordField.setText("");
            translationField.setText("");
        }else {
            setMessageLabel(Color.BLACK, "Pola nie mogą być puste!");
        }
    }

    @FXML
    public void deleteWord(){
        trimTextFieldsContent();

        if(!wordField.getText().isEmpty() && !translationField.getText().isEmpty()){
            DatabaseQueryManager.deleteWordWithTranslation(new WordAndTranslation(wordField.getText(), translationField.getText()));
            initiateWordsAndTranslationsTableView();

            setMessageLabel(Color.RED, "Usunięto słówko!");
            wordField.setText("");
            translationField.setText("");
        }else {
            setMessageLabel(Color.BLACK, "Pola nie mogą być puste!");
        }
    }

    @FXML
    public void changeWord(){
        trimTextFieldsContent();

        if(!wordField.getText().isEmpty() && !translationField.getText().isEmpty()){
            WordAndTranslation oldWordAndTranslation = getSelectedWordWithTranslation();
            WordAndTranslation newWordAndTranslation = new WordAndTranslation(wordField.getText(), translationField.getText());

            DatabaseQueryManager.changeWordWithTranslation(oldWordAndTranslation, newWordAndTranslation);
            initiateWordsAndTranslationsTableView();

            setMessageLabel(Color.DARKORANGE, "Zmieniono słówko!");
            wordField.setText("");
            translationField.setText("");
        }else {
            setMessageLabel(Color.BLACK, "Pola nie mogą być puste!");        }
    }

    private void trimTextFieldsContent(){
        wordField.setText(wordField.getText().trim());
        translationField.setText(translationField.getText().trim());
    }

    @FXML
    public void addWordWhenEnterPressed(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)){
            wordField.requestFocus();
            addNewWord();
        }
    }

    private void setMessageLabel(Color color, String text){
        messageLabel.setTextFill(color);
        messageLabel.setText(text);
    }
}
