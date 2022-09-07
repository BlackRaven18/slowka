package com.arek.language_learning_app;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.text.DecimalFormat;
import java.text.ParsePosition;

public class TextFieldFormatConfigurator {

    public static final DecimalFormat intFormat = new DecimalFormat("#");

    public void configureNumericTextField(TextField textField, DecimalFormat format){
        textField.setTextFormatter(new TextFormatter<>(c ->{
            if(c.getControlNewText().isEmpty()){
                return c;
            }
            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);

            if(object == null || parsePosition.getIndex() < c.getControlNewText().length()){
                return null;
            }else{
                return c;
            }
        }));
    }
}
