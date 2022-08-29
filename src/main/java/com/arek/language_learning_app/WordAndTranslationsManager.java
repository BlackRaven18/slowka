package com.arek.language_learning_app;

import com.arek.database_utils.DatabaseManager;
import javafx.scene.control.Label;

import java.util.*;

public class WordAndTranslationsManager {

    private HashMap<String, ArrayList<String>> wordsAndTranslations;
    private ArrayList<String> wordsAndTranslationsKeys;
    private TranslationOrder translationOrder;
    private Languages selectedLanguage;
    private Label translationOrderLabel;


    public WordAndTranslationsManager(Languages language, TranslationOrder order, Label translationOrderLabel){
        this.selectedLanguage = language;
        this.translationOrder = order;
        this.translationOrderLabel = translationOrderLabel;
        loadWordsAndTranslations();
    }


    private void loadWordsAndTranslations(){

        switch(selectedLanguage){
            case SPANISH:
                DatabaseManager.changeToSpanish();
                break;
            case ENGLISH:
                DatabaseManager.changeToEnglish();
                break;
            default:
                DatabaseManager.changeToSpanish();
                break;
        }

        updateTranslationOrderLabel();

        wordsAndTranslations = DatabaseManager.getWordsAndTranslations(translationOrder);
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
        updateTranslationOrderLabel();

        loadWordsAndTranslations();
    }

    private void updateTranslationOrderLabel(){
        switch(selectedLanguage){
            case SPANISH:
                if(translationOrder == TranslationOrder.NORMAL) translationOrderLabel.setText("Hiszpańsko - Polski");
                else translationOrderLabel.setText("Polsko - Hiszpański");
                break;
            case ENGLISH:
                if(translationOrder == TranslationOrder.NORMAL) translationOrderLabel.setText("Angielsko - Polski");
                else translationOrderLabel.setText("Polsko - Angielski");
                break;
            default:
                if(translationOrder == TranslationOrder.NORMAL) translationOrderLabel.setText("Hiszpańsko - Polski");
                else translationOrderLabel.setText("Polsko - Hiszpański");
                break;
        }
    }

    public void selectSpanishLanguage(){
        selectedLanguage = Languages.SPANISH;
        loadWordsAndTranslations();
    }

    public void selectEnglishLanguage(){
        selectedLanguage = Languages.ENGLISH;
        loadWordsAndTranslations();
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
}
