package com.arek.controllers;

import com.arek.database_utils.DatabaseBackupManager;
import com.arek.language_learning_app.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class DatabaseBackupTabController implements Initializable {

    private DirectoryChooser directoryChooser;
    private File backupDirectory;

    @FXML private Label fileLabel, messageLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        directoryChooser = new DirectoryChooser();

        setFileLabel("");
        setMessageLabel(Color.BLACK, "");
    }

    @FXML
    public void selectBackupFolder(){
        backupDirectory = directoryChooser.showDialog(Main.getMainStage());

        if(backupDirectory != null){
            fileLabel.setText(backupDirectory.getAbsolutePath());
        } else {
            fileLabel.setText("nie wybrano pliku...");
        }
    }

    @FXML
    public void createDatabasesBackup(){
        if(backupDirectory != null) {
            DatabaseBackupManager.makeBackup(backupDirectory, false);
            backupDirectory = null;
            setFileLabel("");
            setMessageLabel(Color.GREEN, "Utworzono kopię baz danych słówek!");
        } else{
            setMessageLabel(Color.RED, "Nie wybrano folderu, w którym ma zostać utworzona kopia zapasowa!");
        }
    }

    private void setMessageLabel(Color color, String text){
        messageLabel.setTextFill(color);
        messageLabel.setText(text);
    }

    private void setFileLabel(String text){
        fileLabel.setText(text);
    }
}
