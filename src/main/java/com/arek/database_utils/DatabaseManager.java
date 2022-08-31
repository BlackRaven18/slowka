package com.arek.database_utils;

import com.arek.language_learning_app.TranslationOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class DatabaseManager {

    private static final String SPANISH_POLISH_DATABASE_URL = "jdbc:sqlite:database/spanish-polish.db";
    private static final String ENGLISH_POLISH_DATABASE_URL = "jdbc:sqlite:database/english-polish.db";

    private static String databaseURL = SPANISH_POLISH_DATABASE_URL;

    private static final String GET_WORDS_NUMBER_QUERRY = "SELECT COUNT(*) FROM SLOWO;";
    private static final String GET_TRANSLATIONS_NUMBER_QUERRY = "SELECT COUNT(*) FROM TLUMACZENIE;";
    private static final String GET_WORDS_WITH_TRANSLATIONS ="SELECT sl.slowo, tl.tlumaczenie " +
            "FROM SLOWO sl, TLUMACZENIE tl " +
            "WHERE sl.id_slowa = tl.id_slowa " +
            "ORDER BY sl.slowo;";

    private static final String GET_WORDS_WITH_TRANSLATIONS_REVERSE = "SELECT tl.tlumaczenie, sl.slowo\n" +
            "FROM slowo sl, tlumaczenie tl\n" +
            "WHERE sl.id_slowa = tl.id_slowa;\n";

    public static int getNewWordId(){
        int wordsNumber = -1;
        String querry = "SELECT MAX(ROWID)FROM SLOWO;";

        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(querry)){
            wordsNumber = resultSet.getInt(1);
        }catch (SQLException e){
            System.err.println("GET_WORDS_NUMBER_QUERRY ERROR!");
        }

        return wordsNumber;
    }

    public static int getNewTranslationId(){
        int translationsNumber = -1;

        String querry = "SELECT MAX(ROWID) FROM TLUMACZENIE;";

        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(querry)){
            translationsNumber = resultSet.getInt(1);
        }catch (SQLException e){
            System.err.println("GET_WORDS_NUMBER_QUERRY ERROR!");
        }

        return translationsNumber;
    }

    public static HashMap<String, ArrayList<String>> getWordsAndTranslations(TranslationOrder order){
        HashMap<String, ArrayList<String>> wordsAndTranslations = new HashMap<>();
        String querry;

        if(order == TranslationOrder.NORMAL){
            querry = GET_WORDS_WITH_TRANSLATIONS;
        } else {
            querry = GET_WORDS_WITH_TRANSLATIONS_REVERSE;
        }

        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(querry)){
            while (resultSet.next()){

                ArrayList<String> translationList;

                if(wordsAndTranslations.containsKey(resultSet.getString(1))){
                    translationList = wordsAndTranslations.get(resultSet.getString(1));
                }else {
                    translationList = new ArrayList<>();
                }

                translationList.add(resultSet.getString(2));
                wordsAndTranslations.put(resultSet.getString(1), translationList);
            }

        }catch (SQLException e){
            System.err.println("GET_WORDS_WITH_TRANSLATIONS ERROR!");
        }

        return wordsAndTranslations;
    }

    public static ArrayList<WordAndTranslation> getWordsAndTranslationsAsList(TranslationOrder order){
        ArrayList<WordAndTranslation> wordAndTranslationList = new ArrayList<>();

        String querry;

        if(order == TranslationOrder.NORMAL){
            querry = GET_WORDS_WITH_TRANSLATIONS;
        } else {
            querry = GET_WORDS_WITH_TRANSLATIONS_REVERSE;
        }

        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(querry)){
            while (resultSet.next()){

                String word = resultSet.getString(1);
                String translation = resultSet.getString(2);
                WordAndTranslation wordAndTranslation = new WordAndTranslation(word, translation);

                wordAndTranslationList.add(wordAndTranslation);
            }

        }catch (SQLException e){
            System.err.println("GET_WORDS_WITH_TRANSLATIONS_AS_LIST ERROR!");
        }

        return wordAndTranslationList;
    }

    public static void addWordWithTranslation(WordAndTranslation wordWithTranslation){

        int newWordId;

        if(isWordInDatabase(wordWithTranslation.getWord())){
            newWordId = getWordId(wordWithTranslation.getWord());

            if(isTranslationInDatabase(newWordId, wordWithTranslation.getTranslation())){
                return;
            }

            addNewTranslation(newWordId, wordWithTranslation.getTranslation());

        } else {
            addNewWord(wordWithTranslation.getWord());
            newWordId = getWordId(wordWithTranslation.getWord());
            addNewTranslation(newWordId, wordWithTranslation.getTranslation());
        }
    }

    private static void addNewWord(String word){
        int newWordId =getNewWordId();
        String querry = String.format("INSERT INTO SLOWO VALUES(%d, '%s');", newWordId, word);

        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement()){
            statement.executeUpdate(querry);

        }catch (SQLException e){
            System.err.println("ADD_NEW_WORD ERROR!");
        }
    }

    private static void addNewTranslation(int wordId, String translation){
        int newTranslationId =getNewTranslationId();
        String querry = String.format("INSERT INTO TLUMACZENIE VALUES(%d, '%s', %d);", newTranslationId, translation, wordId);

        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement()){
            statement.executeUpdate(querry);

        }catch (SQLException e){
            e.printStackTrace();
            System.err.println("ADD_NEW_TRANSLATION ERROR!");
        }
    }

    public static void deleteWordWithTranslation(WordAndTranslation wordAndTranslation){
        WordAndTranslationRowNumbers wordAndTranslationRowNumbers = getWordAndTranslationRowsNumbers(wordAndTranslation);

        String deleteTranslationQuerry = String.format("DELETE FROM TLUMACZENIE WHERE ROWID = %d;", wordAndTranslationRowNumbers.gettranslationRowNumber());
        String deleteWordQuerry = String.format("DELETE FROM SLOWO WHERE ROWID = %d;", wordAndTranslationRowNumbers.getWordRowNumber());

        int numberOfWordTranslations = getWordNumberOfTranslations(wordAndTranslation.getWord());

        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement()){
            statement.executeUpdate(deleteTranslationQuerry);

            if(numberOfWordTranslations == 1) {
                statement.executeUpdate(deleteWordQuerry);
            }

        }catch (SQLException e){
            System.err.println("DELETE_WORD_WITH_TRANSLATION ERROR!");
        }
    }

    public static void changeWordWithTranslation(WordAndTranslation oldWordAndTranslation, WordAndTranslation newWordAndTranslation){
        WordAndTranslationRowNumbers wordAndTranslationRowNumbers = getWordAndTranslationRowsNumbers(oldWordAndTranslation);

        String changeWordQuerry = String.format("UPDATE SLOWO SET slowo = '%s' WHERE ROWID = %d;",
                newWordAndTranslation.getWord(), wordAndTranslationRowNumbers.getWordRowNumber());
        String changeTranslationQuerry = String.format("UPDATE TLUMACZENIE SET tlumaczenie = '%s' WHERE ROWID = %d;",
                newWordAndTranslation.getTranslation(), wordAndTranslationRowNumbers.gettranslationRowNumber());

        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement()){

            statement.executeUpdate(changeWordQuerry);
            statement.executeUpdate(changeTranslationQuerry);

        }catch (SQLException e){
            System.err.println("CHANGE_WORD_WITH_TRANSLATION ERROR!");
        }

    }

    public static int getWordNumberOfTranslations(String word){
        int numberOfTranslations = -1;

        String querry = String.format("SELECT COUNT(*)" +
                " FROM SLOWO sl, TLUMACZENIE tl" +
                " WHERE sl.id_slowa = tl.id_slowa" +
                " AND sl.slowo = '%s';", word);

        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(querry)){

            while(resultSet.next()){
                numberOfTranslations = resultSet.getInt(1);
            }

        }catch (SQLException e){
            System.err.println("GET_WORDS_NUMBER_QUERRY ERROR!");
        }

        return numberOfTranslations;
    }

    public static WordAndTranslationRowNumbers getWordAndTranslationRowsNumbers(WordAndTranslation wordAndTranslation){
        String querry = String.format("SELECT sl.ROWID, tl.ROWID " +
                "FROM SLOWO sl, TLUMACZENIE tl " +
                "WHERE sl.id_slowa = tl.id_slowa " +
                "AND sl.slowo = '%s' " +
                "AND tl.tlumaczenie = '%s';",
                wordAndTranslation.getWord(), wordAndTranslation.getTranslation());

        WordAndTranslationRowNumbers wordAndTranslationRowNumbers = new WordAndTranslationRowNumbers();

        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(querry)){

            while (resultSet.next()){
                wordAndTranslationRowNumbers = new WordAndTranslationRowNumbers(resultSet.getInt(1), resultSet.getInt(2));
            }

        }catch (SQLException e){
            System.err.println("GET_WORD_AND_TRANSLATION_ROWS_NUMBER ERROR!");
        }
        return wordAndTranslationRowNumbers;
    }


    private static int getWordId(String word){
        String querry = String.format("SELECT id_slowa FROM SLOWO WHERE slowo = '%s';", word);

        int id = -1;
        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(querry)){

            while (resultSet.next()){
                id = resultSet.getInt(1);
            }

        }catch (SQLException e){
            System.err.println("GET_WORD_ID ERROR!");
        }
        return id;
    }

    private static int getTranslationId(int wordId, String translation){
        String querry = String.format("SELECT id_tlumaczenia FROM TLUMACZENIE WHERE id_slowa = %d AND tlumaczenie = '%s';", wordId, translation);

        int id = -1;

        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(querry)){

            while (resultSet.next()){
                id = resultSet.getInt(1);
            }

        }catch (SQLException e){
            System.err.println("GET_TRANSLATION_ID ERROR!");
        }

        return id;
    }

    private static boolean isWordInDatabase(String word){
        return getWordId(word) != -1;
    }

    private static boolean isTranslationInDatabase(int wordId, String translation){
        return getTranslationId(wordId, translation) != -1;
    }

    public static void changeToSpanish(){
        databaseURL = SPANISH_POLISH_DATABASE_URL;
    }
    public static void changeToEnglish(){
        databaseURL = ENGLISH_POLISH_DATABASE_URL;
    }
}
