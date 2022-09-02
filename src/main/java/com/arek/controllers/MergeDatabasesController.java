package com.arek.controllers;

import com.arek.database_utils.DatabaseBackupManager;
import com.arek.database_utils.DatabaseManager;
import com.arek.database_utils.DatabaseMergeManager;
import com.arek.language_learning_app.Languages;
import com.arek.language_learning_app.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MergeDatabasesController implements Initializable {

    private FileChooser fileChooser;
    private File databaseFile;
    private Languages language;

    @FXML private MenuButton selectLanguageMenu;
    @FXML private Label fileLabel, messageLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileLabel.setText("");
        messageLabel.setText("");

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Database Files", "*.db"));
        language = Languages.SPANISH;
    }

    @FXML
    public void addDatabaseFile(){
        databaseFile = fileChooser.showOpenDialog(Main.getMainStage());
        if(databaseFile != null){
            fileLabel.setText(databaseFile.getName());
        }
    }

    public void resetDatabaseFile(){
        databaseFile = null;
        fileLabel.setText("");
    }

    @FXML
    public void mergeDatabases(){

        if(databaseFile != null){
            DatabaseBackupManager.makeCopy();
            DatabaseMergeManager databaseMergeManager = new DatabaseMergeManager(language, databaseFile);

            databaseMergeManager.mergeDatabases();
            messageLabel.setText("Połączono bazy słówek!");
        }
    }

    @FXML
    public void selectSpanishLanguage(){
        selectLanguageMenu.setText("Hiszpański");
        language = Languages.SPANISH;
        DatabaseManager.changeToSpanish();
        resetDatabaseFile();
    }

    @FXML
    public void selectEnglishLanguage(){
        selectLanguageMenu.setText("Angielski");
        language = Languages.ENGLISH;
        DatabaseManager.changeToEnglish();
        resetDatabaseFile();
    }
}
