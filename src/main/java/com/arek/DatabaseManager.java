package com.arek;

import java.sql.*;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:wordsdatabase.db";

    private static final String GET_WORDS_NUMBER_QUERRY = "SELECT COUNT(*) FROM SLOWO;";

    public static int getWordsNumber(){
        int wordsNumber = 0;

        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_WORDS_NUMBER_QUERRY)){
            wordsNumber = resultSet.getInt(1);
            System.out.println(wordsNumber);
        }catch (SQLException e){
            System.err.println("GET_WORDS_NUMBER_QUERRY ERROR!");
        }

        return wordsNumber;
    }
}
