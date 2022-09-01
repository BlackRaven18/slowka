package com.arek.database_utils;

import javax.swing.plaf.IconUIResource;
import java.io.File;
import java.util.ArrayList;

public class DatabaseMergeManager {

    File newDatabaseFile;

    ArrayList<Word> srcWordTable;
    ArrayList<Word> newWordTable;
    ArrayList<Translation> srcTranslationTable;
    ArrayList<Translation> newTranslationTable;


    public DatabaseMergeManager(File newDatabaseFile){
        this.newDatabaseFile = newDatabaseFile;

        srcWordTable = loadWordTable();
        srcTranslationTable = loadTranslationTable();

        String URL = String.format("jdbc:sqlite:%s", this.newDatabaseFile.getAbsolutePath());

        DatabaseManager.changeToOtherDatabase(URL);

        newWordTable = loadWordTable();
        newTranslationTable = loadTranslationTable();

        for(Word word : srcWordTable){
            System.out.println(word.getWordID() +  " " + word.getWord());
        }

        System.out.println("*************************************************");

        for(Translation translation : srcTranslationTable){
            System.out.println(translation.getTranslationID() + " "
                    + translation.getTranslation() + " " + translation.getWordID());
        }

        System.out.println("-----------------------------------------------------");

    }

    private ArrayList<Word> loadWordTable(){
        return DatabaseManager.getWords();
    }

    private ArrayList<Translation> loadTranslationTable(){
        return DatabaseManager.getTranslations();
    }

    public void mergeDatabases(){
        //1. update src Translations Table Primary Keys
        updateTranslationTablePrimaryKeys();

        //2. update Word Table Primary Keys and Foreign Keys in Translation Table
        updateWordTablePrimaryKeysAndForeignKeysInTranslationTable();

        //3. add words from new database to old database
        addWordsAndTranslationsFromNewDatabase();

        System.out.println("---------------- AFTER -----------------");

        for(Word word : srcWordTable){
            System.out.println(word.getWordID() +  " " + word.getWord());
        }

        System.out.println("*************************************************");

        for(Translation translation : srcTranslationTable){
            System.out.println(translation.getTranslationID() + " "
                    + translation.getTranslation() + " " + translation.getWordID());
        }



    }

    private void updateTranslationTablePrimaryKeys(){
        int counter = 0;

        for(Translation translation : srcTranslationTable){
            translation.setTranslationID(counter);
            counter++;
        }
    }

    private void updateWordTablePrimaryKeysAndForeignKeysInTranslationTable() {

        int counter = 0;

        for (Word word : srcWordTable) {
            for (Translation translation : srcTranslationTable) {
                if (translation.getWordID() == word.getWordID()) {
                    translation.setWordID(counter);
                }
            }
            word.setWordID(counter);
            counter++;

        }
    }

    private void addWordsAndTranslationsFromNewDatabase(){

        for(Word newWord : newWordTable){

            // if new word is already in the souce table
            Word oldWord = isWordInWordTable(srcWordTable, newWord.getWord());
            if(oldWord != null){

                ArrayList<Translation> newWordTranslations = getWordTranslationList(newWord, newTranslationTable);

                for(Translation translation : newWordTranslations){

                    //if this translations does not existing in src database add it
                    if(!isTranslationInTranslationTable(translation, srcTranslationTable)){
                        Translation newTranslation = new Translation(srcTranslationTable.size(),
                                translation.getTranslation(), oldWord.getWordID());
                        srcTranslationTable.add(newTranslation);
                    }
                }

                // if new word does not exist is src database add it and add translations
            } else{

                int newWordId = srcWordTable.size();

                //add new word
                srcWordTable.add(new Word(newWordId, newWord.getWord()));

                //add translations
                ArrayList<Translation> newWordTranslations = getWordTranslationList(newWord, newTranslationTable);

                for(Translation translation : newWordTranslations){
                    srcTranslationTable.add(new Translation(srcTranslationTable.size(),
                            translation.getTranslation(), newWordId));
                }

            }
        }

    }

    private Word isWordInWordTable(ArrayList<Word> wordTable, String wordName){

        for(Word word : wordTable){
            if(word.getWord().equals(wordName)){
                return word;
            }
        }

        return null;
    }


    private boolean isTranslationInTranslationTable(Translation translation, ArrayList<Translation> translationTable){
        for(Translation tmpTranslation : translationTable){
            if(tmpTranslation.getTranslation().equals(translation.getTranslation())){
                return true;
            }
        }

        return false;
    }

    private ArrayList<Translation> getWordTranslationList(Word word, ArrayList<Translation> translationList){
        ArrayList<Translation> wordTranslationList = new ArrayList<>();

        for(Translation translation : translationList){
            if(translation.getWordID() == word.getWordID()){
                wordTranslationList.add(translation);
            }
        }

        return wordTranslationList;
    }



}
