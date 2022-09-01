package com.arek.controllers;

import com.arek.database_utils.DatabaseBackupManager;
import com.arek.database_utils.DatabaseManager;
import com.arek.database_utils.DatabaseMergeManager;
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

    @FXML private MenuButton selectLanguageMenu;
    @FXML private Label fileLabel, messageLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileLabel.setText("");
        messageLabel.setText("");

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Database Files", "*.db"));

        //TODO: ONLY FOR TESTS!!
        databaseFile = new File("C:\\Users\\Arek\\Desktop\\wordsdatabase.db");

    }

    @FXML
    public void addDatabaseFile(){
        databaseFile = fileChooser.showOpenDialog(Main.getMainStage());
        if(databaseFile != null){
            fileLabel.setText(databaseFile.getName());
        }
    }

    @FXML
    public void mergeDatabases(){

        if(databaseFile != null){
            DatabaseBackupManager.makeCopy();
            DatabaseMergeManager databaseMergeManager = new DatabaseMergeManager(databaseFile);

            databaseMergeManager.mergeDatabases();
        }
    }

    @FXML
    public void selectSpanishLanguage(){
        selectLanguageMenu.setText("Hiszpański");
        DatabaseManager.changeToSpanish();
    }

    @FXML
    public void selectEnglishLanguage(){
        selectLanguageMenu.setText("Angielski");
        DatabaseManager.changeToEnglish();
    }
}
