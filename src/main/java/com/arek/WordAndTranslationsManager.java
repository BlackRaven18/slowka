package com.arek;

import java.util.*;

public class WordAndTranslationsManager {
    private HashMap<String, ArrayList<String>> wordsAndTranslations;
    private ArrayList<String> wordsAndTranslationsKeys;


    public WordAndTranslationsManager(){
        loadWordsAndTranslations();
    }

    private void loadWordsAndTranslations(){
        wordsAndTranslations = DatabaseManager.getWordsAndTranslations();
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

    public HashMap<String, ArrayList<String>> getWordsAndTranslations() {
        return wordsAndTranslations;
    }

    public ArrayList<String> getWordsAndTranslationsKeys() {
        return wordsAndTranslationsKeys;
    }
}
