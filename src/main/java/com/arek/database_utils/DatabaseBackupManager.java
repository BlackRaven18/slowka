package com.arek.database_utils;

import java.io.*;
import java.util.ArrayList;

public class DatabaseBackupManager {

    private static final String DB_DIRECTORY = "database";
    private static final String DB_BACKUP_DIRECTORY = "dbbackup";
    private static final String DB_BACKUP_DESKTOP_DIRECTORY = System.getProperty("user.home") + File.separator +  "Desktop" + File.separator + "slowka-db-backup";
    private static final String SPANISH_POLISH_DB_FILE = "spanish-polish.db";
    private static final String ENGLISH_POLISH_DB_FILE = "english-polish.db";

    public static void makeCopy(){
        makeDatabaseCopy(DB_BACKUP_DIRECTORY, SPANISH_POLISH_DB_FILE);
        makeDatabaseCopy(DB_BACKUP_DESKTOP_DIRECTORY, ENGLISH_POLISH_DB_FILE);

        //desktop copy
        File desktopDir = new File(String.format("%s", DB_BACKUP_DESKTOP_DIRECTORY));
        makeDesktopBackupDirectory(desktopDir);

        makeDatabaseCopy(DB_BACKUP_DESKTOP_DIRECTORY, SPANISH_POLISH_DB_FILE);
        makeDatabaseCopy(DB_BACKUP_DESKTOP_DIRECTORY, ENGLISH_POLISH_DB_FILE);

    }


    private static void makeDatabaseCopy(String directoryPath, String databaseName){
        String separator = File.separator;

        File src = new File(String.format("%s%s%s", DB_DIRECTORY, separator, databaseName));
        File dest = new File(String.format("%s%s%s", directoryPath, separator, databaseName));

        try(InputStream is = new FileInputStream(src);
            OutputStream os = new FileOutputStream(dest)){
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buf)) > 0){
                os.write(buf, 0, bytesRead);
            }
        }catch (IOException e){
            e.printStackTrace();
            System.err.println("DB BACKUP ERROR");
        }
    }

    private static void makeDesktopBackupDirectory(File desktopFile){
        if(desktopFile.exists() && desktopFile.isDirectory()){
            return;
        }

        if(desktopFile.mkdirs()){
            System.out.println("file created");
        }

    }

}
