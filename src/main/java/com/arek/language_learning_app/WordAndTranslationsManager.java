package com.arek.language_learning_app;

import com.arek.database_utils.DatabaseQueryManager;

import java.util.*;

public class WordAndTranslationsManager {

    private HashMap<String, ArrayList<String>> wordsAndTranslations;
    private ArrayList<String> wordsAndTranslationsKeys;
    private TranslationOrder translationOrder;
    private Languages selectedLanguage;


    public WordAndTranslationsManager(Languages language, TranslationOrder order){
        this.selectedLanguage = language;
        this.translationOrder = order;
        loadWordsAndTranslations();
    }

    private void reloadWordsAndTranslations(){
        loadWordsAndTranslations();
    }


    private void loadWordsAndTranslations(){

        switch(selectedLanguage){
            case SPANISH:
                DatabaseQueryManager.changeToSpanish();
                break;
            case ENGLISH:
                DatabaseQueryManager.changeToEnglish();
                break;
        }

        wordsAndTranslations = DatabaseQueryManager.getWordsAndTranslations(translationOrder);
        loadWordsAndTranslationsKeys();
    }

    private void loadWordsAndTranslationsKeys(){
        wordsAndTranslationsKeys = new ArrayList<>(wordsAndTranslations.keySet());
    }

    public String getRandomWord(){
        int i;

        Random random = new Random();

        // if there is no more words it should be loaded again
        if(wordsAndTranslations.size() == 0){
            loadWordsAndTranslations();
            loadWordsAndTranslationsKeys();
        }

        i = random.nextInt(wordsAndTranslations.size());
        return wordsAndTranslationsKeys.get(i);
    }

    public boolean checkTranslation(String word, String translation){
        ArrayList<String> correctTranslations = wordsAndTranslations.get(word);

        for(String correctTranslation : correctTranslations){
            if(translation.equals(correctTranslation)){
                //deleting the used word
                removeUsedWord(word);
                return true;
            }
        }
        return false;
    }

    private void removeUsedWord(String key){
        wordsAndTranslations.remove(key);

        for(int i = 0; i < wordsAndTranslationsKeys.size(); i++){
            if(wordsAndTranslationsKeys.get(i).equals(key)){
                wordsAndTranslationsKeys.remove(i);
                return;
            }
        }
    }

    public void changeTranslationOrder(TranslationOrder order){
        this.translationOrder = order;
        reloadWordsAndTranslations();
    }

    public void selectSpanishLanguage(){
        selectedLanguage = Languages.SPANISH;
        reloadWordsAndTranslations();
    }

    public void selectEnglishLanguage(){
        selectedLanguage = Languages.ENGLISH;
        reloadWordsAndTranslations();
    }

    public HashMap<String, ArrayList<String>> getWordsAndTranslations() {
        return wordsAndTranslations;
    }

    public ArrayList<String> getWordsAndTranslationsKeys() {
        return wordsAndTranslationsKeys;
    }

    public TranslationOrder getTranslationOrder() {
        return translationOrder;
    }

    public Languages getSelectedLanguage() {
        return selectedLanguage;
    }

    public void setSelectedLanguage(Languages selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
    }

    public String getWordTranslations(String word){
        ArrayList<String> translations = wordsAndTranslations.get(word);
        StringBuilder translationsList = new StringBuilder();
        for(String translation : translations){
            translationsList.append(translation).append("\n");
        }
        return translationsList.toString();
    }
}
