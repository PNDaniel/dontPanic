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

        ArrayList<Event> events = Event.getEventsFromCursor(c);

        c.close();
        db.close();

        return events;
    }

    public Event getEvent(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(SQLHelper.updateEntry(Event.EventEntry.TABLE_NAME, Event.EventEntry.COLUMN_ID ,id), null);

        ArrayList<Event> events = Event.getEventsFromCursor(c);

        c.close();
        db.close();
        if (events != null && events.size() > 0){
            return events.get(0);
        }

        return null;
    }

    public void updateTime(int eventID, long time){
        SQLiteDatabase db = this.getReadableDatabase();

    }

    /*public void setTimeSpent(int taskID, long timeSpent) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Task.COLUMN_TIMESPENT, timeSpent);
        db.update(Task.TABLE_NAME, cv, Task.COLUMN_ID + "=" + taskID, null);
    }*/
}