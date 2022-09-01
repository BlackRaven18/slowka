package com.arek.database_utils;

public class Translation {
    private int translationID;
    private String translation;
    private int wordID;

    public Translation(int translationID, String translation, int wordID) {
        this.translationID = translationID;
        this.translation = translation;
        this.wordID = wordID;
    }

    public int getTranslationID() {
        return translationID;
    }

    public void setTranslationID(int translationID) {
        this.translationID = translationID;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public int getWordID() {
        return wordID;
    }

    public void setWordID(int wordID) {
        this.wordID = wordID;
    }
}
