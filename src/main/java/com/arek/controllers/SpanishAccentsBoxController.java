package com.arek.controllers;

import com.arek.language_learning_app.AppOptions;
import com.arek.language_learning_app.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class SpanishAccentsBoxController implements Initializable {

    private TextField focusedTextField;
    private AppOptions options;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        options = AppOptions.getInstance();
        focusedTextField = options.getLastFocusedTextField();

    }

    private void insertAccent(char accent){
        //get last focused text field
        focusedTextField = options.getLastFocusedTextField();
        if(focusedTextField == null){
            return;
        }

        int caretPosition = options.getLastFocusedTextFieldCaretPosition();
        StringBuilder builder = new StringBuilder(focusedTextField.getText());
        builder.insert(caretPosition, accent);
        focusedTextField.setText(builder.toString());

        //request focus on TextField and set caret position to next position after new sign added
        focusedTextField.requestFocus();
        focusedTextField.positionCaret(caretPosition + 1);
    }


    @FXML
    public void insertFirstAccent(){
        insertAccent('á');
    }

    public TextField getFocusedTextField() {
        return focusedTextField;
    }

    public void setFocusedTextField(TextField focusedTextField) {
        this.focusedTextField = focusedTextField;
    }
}
