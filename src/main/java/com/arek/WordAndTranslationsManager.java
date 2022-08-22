package com.arek;

import java.util.ArrayList;
import java.util.HashMap;

public class WordAndTranslationsManager {
    private HashMap<String, ArrayList<String>> wordsAndTranslations;

    public WordAndTranslationsManager(){
        loadWordsAndTranslations();
    }

    private void loadWordsAndTranslations(){
        wordsAndTranslations = DatabaseManager.getWordsAndTranslations();
    }

    public HashMap<String, ArrayList<String>> getWordsAndTranslations() {
        return wordsAndTranslations;
    }
}
