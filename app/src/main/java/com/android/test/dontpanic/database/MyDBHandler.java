package com.android.test.dontpanic.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Daniel Nunes on 19-07-2016.
 */

//TODO 1: understand what is SQLiteOpenHelper
//TODO 1: finish this class, duh.
public class MyDBHandler extends SQLiteOpenHelper {

    private static MyDBHandler dbHandler;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dontpanic.db";

    public MyDBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized MyDBHandler getInstance(Context context) {
        if (dbHandler == null) {
            dbHandler = new MyDBHandler(context.getApplicationContext());
        }
        return dbHandler;
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(new SQLHelper().getSqlCreateEntries());
    }

    //TODO 1: understand where to use oldVersion/newVersion
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(new SQLHelper().getSqlCreateEntries());
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long insertEvent(Event event){

        long lastID;
        SQLiteDatabase db = this.getWritableDatabase();
        lastID = db.insert(Event.EventEntry.TABLE_NAME, null, event.getContentValues());

        db.close();

        return lastID;
    }

    public ArrayList<Event> getAllEvents() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(SQLHelper.selectAllEvents(Event.EventEntry.TABLE_NAME), null);

        ArrayList<Event> tasks = Event.getTasksFromCursor(c);

        c.close();
        db.close();

        return tasks;
    }
}