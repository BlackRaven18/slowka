package com.arek.database_utils;

public class Word {
    private int wordID;
    private String word;

    public Word(int wordID, String word) {
        this.wordID = wordID;
        this.word = word;
    }

    public int getWordID() {
        return wordID;
    }

    public void setWordID(int wordID) {
        this.wordID = wordID;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
