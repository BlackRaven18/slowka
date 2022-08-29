package com.arek.database_utils;

public class WordAndTranslationRowNumbers {
    int wordRowNumber;
    int translationWordNumber;

    public WordAndTranslationRowNumbers(){}

    public WordAndTranslationRowNumbers(int wordRowNumber, int translationWordNumber){
        this.wordRowNumber = wordRowNumber;
        this.translationWordNumber = translationWordNumber;
    }

    public int getWordRowNumber() {
        return wordRowNumber;
    }

    public void setWordRowNumber(int wordRowNumber) {
        this.wordRowNumber = wordRowNumber;
    }

    public int getTranslationWordNumber() {
        return translationWordNumber;
    }

    public void setTranslationWordNumber(int translationWordNumber) {
        this.translationWordNumber = translationWordNumber;
    }
}
