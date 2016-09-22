package com.android.test.dontpanic.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Daniel Nunes on 18-08-2016.
 */
public class SQLHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + Event.EventEntry.TABLE_NAME + " ("+
            Event.EventEntry._ID + INTEGER_TYPE + " PRIMARY KEY," +
            Event.EventEntry.COLUMN_ID + TEXT_TYPE + COMMA_SEP +
            Event.EventEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
            Event.EventEntry.COLUMN_DAY + INTEGER_TYPE + COMMA_SEP +
            Event.EventEntry.COLUMN_MONTH + INTEGER_TYPE + COMMA_SEP +
            Event.EventEntry.COLUMN_YEAR + INTEGER_TYPE + COMMA_SEP +
            Event.EventEntry.COLUMN_HOUR + INTEGER_TYPE + COMMA_SEP +
            Event.EventEntry.COLUMN_MINUTE + INTEGER_TYPE + COMMA_SEP +
            Event.EventEntry.COLUMN_FINISHED + INTEGER_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Event.EventEntry.TABLE_NAME;

    public String getSqlCreateEntries(){
        return SQL_CREATE_ENTRIES;
    }
    public String getSqlDeleteEntries(){
        return SQL_DELETE_ENTRIES;
    }

    public static String selectAllEvents(String tableName){
        return "SELECT * FROM " + tableName;
    }
}

