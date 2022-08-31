package com.arek.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;

import java.net.URL;
import java.util.ResourceBundle;

public class MergeDatabasesController implements Initializable {

    @FXML private MenuButton selectLanguageMenu;
    @FXML private Label fileLabel, messageLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileLabel.setText("");
        messageLabel.setText("");

    }

    @FXML
    public void addDatabaseFile(){

    }

    @FXML
    public void mergeDatabases(){

    }
}
