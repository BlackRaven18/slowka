package com.arek.database_utils;

import java.util.ArrayList;

public class QueryResult {

    private final ArrayList<ArrayList<String>> queryResult;

    public QueryResult(){
        queryResult = new ArrayList<>();
    };

    public void addNewQueryLine(ArrayList<String> line){
        queryResult.add(line);
    }

    public ArrayList<String> getQueryLine(int index){
        return queryResult.get(index);
    }

    public int getQueryLines(){
        return queryResult.size();
    }
    public boolean isEmpty(){
        return queryResult.isEmpty();
    }


}
