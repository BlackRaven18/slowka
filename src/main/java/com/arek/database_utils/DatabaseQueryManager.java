package com.arek.database_utils;

import com.arek.language_learning_app.TranslationOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseQueryManager {


    private static String databaseURL = DatabaseInfoManager.SPANISH_POLISH_DATABASE_URL;

    private static final String GET_WORDS_WITH_TRANSLATIONS ="SELECT sl.slowo, tl.tlumaczenie " +
            "FROM SLOWO sl, TLUMACZENIE tl " +
            "WHERE sl.id_slowa = tl.id_slowa " +
            "ORDER BY sl.slowo;";

    private static final String GET_WORDS_WITH_TRANSLATIONS_REVERSE = "SELECT tl.tlumaczenie, sl.slowo\n" +
            "FROM slowo sl, tlumaczenie tl\n" +
            "WHERE sl.id_slowa = tl.id_slowa;\n";


    public static QueryResult getQueryResult(String query){
        QueryResult queryResult = new QueryResult();
        ArrayList<String> line;

        try(Connection connection = DriverManager.getConnection(databaseURL);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)){

            ResultSetMetaData rsmd = resultSet.getMetaData();

            while (resultSet.next()) {
                line = new ArrayList<>();

                for(int i = 1; i <= rsmd.getColumnCount(); i++){
                    line.add(resultSet.getString(i));
                }

                queryResult.addNewQueryLine(line);
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.err.println("query RESULT ERROR");
        }

        return queryResult;
    }

    public static void executeQuery(String query){
        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement()){
            statement.executeUpdate(query);

        }catch (SQLException e){
            e.printStackTrace();
            System.err.println("EXECUTE query ERROR");
        }
    }

    public static ArrayList<Word> getWords(){
        String query = "SELECT * FROM SLOWO";
        QueryResult queryResult = getQueryResult(query);

        ArrayList<Word> wordList = new ArrayList<>();
        for(int i = 0; i < queryResult.getQueryLines(); i++){
            ArrayList<String> line = queryResult.getQueryLine(i);
            Word word = new Word(Integer.parseInt(line.get(0)), line.get(1));
            wordList.add(word);
        }

        return wordList;
    }

    public static ArrayList<Translation> getTranslations(){
        String query = "SELECT * FROM TLUMACZENIE";
        QueryResult queryResult = getQueryResult(query);

        ArrayList<Translation> translationList = new ArrayList<>();
        for(int i = 0; i < queryResult.getQueryLines(); i++){
            ArrayList<String> line = queryResult.getQueryLine(i);
            Translation translation = new Translation(Integer.parseInt(line.get(0)), line.get(1), Integer.parseInt(line.get(2)));
            translationList.add(translation);
        }
        return translationList;
    }

    public static int getNewWordId(){
        String query = "SELECT MAX(ROWID)FROM SLOWO;";
        QueryResult queryResult = getQueryResult(query);

        return Integer.parseInt(queryResult.getQueryLine(0).get(0));
    }

    public static int getNewTranslationId(){
        String query = "SELECT MAX(ROWID) FROM TLUMACZENIE;";
        QueryResult queryResult = getQueryResult(query);

        return Integer.parseInt(queryResult.getQueryLine(0).get(0));
    }

    public static HashMap<String, ArrayList<String>> getWordsAndTranslations(TranslationOrder order){
        HashMap<String, ArrayList<String>> wordsAndTranslations = new HashMap<>();
        String query;
        QueryResult queryResult;

        if(order == TranslationOrder.NORMAL){
            query = GET_WORDS_WITH_TRANSLATIONS;
        } else {
            query = GET_WORDS_WITH_TRANSLATIONS_REVERSE;
        }

        queryResult = getQueryResult(query);
        for(int i = 0; i < queryResult.getQueryLines(); i++){
            ArrayList<String> translationList;

            if(wordsAndTranslations.containsKey(queryResult.getQueryLine(i).get(0))){
                translationList = wordsAndTranslations.get(queryResult.getQueryLine(i).get(0));
            }else {
                translationList = new ArrayList<>();
            }

            translationList.add(queryResult.getQueryLine(i).get(1));
            wordsAndTranslations.put(queryResult.getQueryLine(i).get(0), translationList);


        }
        return wordsAndTranslations;
    }

    public static ArrayList<WordAndTranslation> getWordsAndTranslationsAsList(TranslationOrder order){
        ArrayList<WordAndTranslation> wordAndTranslationList = new ArrayList<>();
        QueryResult queryResult;

        String query;

        if(order == TranslationOrder.NORMAL){
            query = GET_WORDS_WITH_TRANSLATIONS;
        } else {
            query = GET_WORDS_WITH_TRANSLATIONS_REVERSE;
        }

        queryResult = getQueryResult(query);
        for(int i = 0; i < queryResult.getQueryLines(); i++){
            String word = queryResult.getQueryLine(i).get(0);
            String translation = queryResult.getQueryLine(i).get(1);
            wordAndTranslationList.add(new WordAndTranslation(word, translation));
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
        int newWordId = getNewWordId();
        String query = String.format("INSERT INTO SLOWO VALUES(%d, '%s');", newWordId, word);

        executeQuery(query);
    }

    private static void addNewTranslation(int wordId, String translation){
        int newTranslationId =getNewTranslationId();
        String query = String.format("INSERT INTO TLUMACZENIE VALUES(%d, '%s', %d);", newTranslationId, translation, wordId);

        executeQuery(query);
    }

    public static void deleteWordWithTranslation(WordAndTranslation wordAndTranslation){
        WordAndTranslationRowNumbers wordAndTranslationRowNumbers = getWordAndTranslationRowsNumbers(wordAndTranslation);

        if(wordAndTranslationRowNumbers == null){
            return;
        }

        String deleteTranslationQuery = String.format("DELETE FROM TLUMACZENIE WHERE ROWID = %d;", wordAndTranslationRowNumbers.gettranslationRowNumber());
        String deleteWordQuery = String.format("DELETE FROM SLOWO WHERE ROWID = %d;", wordAndTranslationRowNumbers.getWordRowNumber());

        int numberOfWordTranslations = getWordNumberOfTranslations(wordAndTranslation.getWord());

        executeQuery(deleteTranslationQuery);
        if(numberOfWordTranslations == 1){
            executeQuery(deleteWordQuery);
        }
    }

    public static void changeWordWithTranslation(WordAndTranslation oldWordAndTranslation, WordAndTranslation newWordAndTranslation) {
        WordAndTranslationRowNumbers wordAndTranslationRowNumbers = getWordAndTranslationRowsNumbers(oldWordAndTranslation);

        String changeWordQuery = String.format("UPDATE SLOWO SET slowo = '%s' WHERE ROWID = %d;",
                newWordAndTranslation.getWord(), wordAndTranslationRowNumbers.getWordRowNumber());
        String changeTranslationQuery = String.format("UPDATE TLUMACZENIE SET tlumaczenie = '%s' WHERE ROWID = %d;",
                newWordAndTranslation.getTranslation(), wordAndTranslationRowNumbers.gettranslationRowNumber());

        executeQuery(changeWordQuery);
        executeQuery(changeTranslationQuery);
    }

    public static int getWordNumberOfTranslations(String word){

        String query = String.format("SELECT COUNT(*)" +
                " FROM SLOWO sl, TLUMACZENIE tl" +
                " WHERE sl.id_slowa = tl.id_slowa" +
                " AND sl.slowo = '%s';", word);

        QueryResult queryResult = getQueryResult(query);
        return Integer.parseInt(queryResult.getQueryLine(0).get(0));
    }

    public static WordAndTranslationRowNumbers getWordAndTranslationRowsNumbers(WordAndTranslation wordAndTranslation){
        String query = String.format("SELECT sl.ROWID, tl.ROWID " +
                "FROM SLOWO sl, TLUMACZENIE tl " +
                "WHERE sl.id_slowa = tl.id_slowa " +
                "AND sl.slowo = '%s' " +
                "AND tl.tlumaczenie = '%s';",
                wordAndTranslation.getWord(), wordAndTranslation.getTranslation());

        QueryResult queryResult = getQueryResult(query);

        if(queryResult.isEmpty()) {
            return null;
        }

        return new WordAndTranslationRowNumbers(Integer.parseInt(queryResult.getQueryLine(0).get(0)),
                Integer.parseInt(queryResult.getQueryLine(0).get(1)));
    }


    private static int getWordId(String word){
        String query = String.format("SELECT id_slowa FROM SLOWO WHERE slowo = '%s';", word);
        QueryResult queryResult = getQueryResult(query);

        if(!queryResult.isEmpty()){
            return Integer.parseInt(queryResult.getQueryLine(0).get(0));
        } else {
            return -1;
        }
    }

    private static int getTranslationId(int wordId, String translation){
        String query = String.format("SELECT id_tlumaczenia FROM TLUMACZENIE WHERE id_slowa = %d AND tlumaczenie = '%s';", wordId, translation);
        QueryResult queryResult = getQueryResult(query);

        if(!queryResult.isEmpty()){
            return Integer.parseInt(queryResult.getQueryLine(0).get(0));
        } else {
            return -1;
        }
    }

    private static boolean isWordInDatabase(String word){
        return getWordId(word) != -1;
    }

    private static boolean isTranslationInDatabase(int wordId, String translation){
        return getTranslationId(wordId, translation) != -1;
    }

    public static void clearDatabase(){
        clearTranslationTable();
        clearWordTable();
    }

    private static void clearTranslationTable(){
        String query = "DELETE FROM TLUMACZENIE";

        executeQuery(query);
    }

    private static void clearWordTable(){
        String query = "DELETE FROM SLOWO";

        executeQuery(query);
    }

    public static void addWordsToWordTable(ArrayList<Word> wordList){
        String query;

        try(Connection connection = DriverManager.getConnection(databaseURL);
            Statement statement = connection.createStatement()){

            for(Word word : wordList){
                query = String.format("INSERT INTO SLOWO VALUES (%d, '%s');", word.getWordID(), word.getWord());
                statement.executeUpdate(query);
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.err.println("ADD WORDS TO WORD TABLE ERROR");
        }
    }


    public static void addTranslationsToTranslationTable(ArrayList<Translation> translationList){
        String query;

        try(Connection connection = DriverManager.getConnection(databaseURL);
            Statement statement = connection.createStatement()){

            for(Translation translation : translationList){
                query = String.format("INSERT INTO TLUMACZENIE VALUES (%d, '%s', %d);", translation.getTranslationID(), translation.getTranslation(), translation.getWordID());
                statement.executeUpdate(query);
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.err.println("ADD TRANSLATION TO TRANSLATION TABLE ERROR");
        }
    }



    public static void changeToSpanish(){
        databaseURL = DatabaseInfoManager.SPANISH_POLISH_DATABASE_URL;
    }
    public static void changeToEnglish(){
        databaseURL = DatabaseInfoManager.ENGLISH_POLISH_DATABASE_URL;
    }
    public static void changeToOtherDatabase(String URL){
        databaseURL = URL;
    }
}
