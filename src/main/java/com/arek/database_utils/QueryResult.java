package com.arek.database_utils;

import java.util.ArrayList;

public class QueryResult {

    private final ArrayList<ArrayList<String>> querryResult;

    public QueryResult(){
        querryResult = new ArrayList<>();
    };

    public void addNewQuerryLine(ArrayList<String> line){
        querryResult.add(line);
    }

    public ArrayList<String> getQuerryLine(int index){
        return querryResult.get(index);
    }

    public void writeQuerryResult(){

        for(int i = 0; i < querryResult.size(); i++){
            for(int j = 0; j < querryResult.get(i).size(); j++){
                System.out.print(querryResult.get(i).get(j) + " ");
            }
            System.out.println("");
        }
    }

    public int getQuerryLines(){
        return querryResult.size();
    }


}
