package com.android.test.dontpanic.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Daniel Nunes on 18-08-2016.
 */
public class Event implements Comparable<Event>{

    public static abstract class EventEntry implements BaseColumns{
        public static final String TABLE_NAME = "event";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DAY = "day";
        public static final String COLUMN_MONTH = "month";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_HOUR = "hour";
        public static final String COLUMN_MINUTE = "minute";
        public static final String COLUMN_FINISHED = "isFinished";
    }

    enum Fields {
        _ID,
        ID,
        NAME,
        DAY,
        MONTH,
        YEAR,
        HOUR,
        MINUTE,
        ISFINISHED
    }

    /**  Event fields **/
    private int id;
    private String name;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    private int isFinished;
    private long remainingTime;
    private long deadlineDate;

    /**
     * Constructor
     */

    public Event(){}

    public Event(int id, String name, int day, int month, int year, int hour, int minute){
        this.id = id;
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        setDeadlineDate();
        this.remainingTime = calculateRemainingTime();
    }

    /**
     * Set Methods
     */
    public void setFinished(int finished) {
        isFinished = finished;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setRemainingTime(long remainingTime){
        this.remainingTime = remainingTime;
    }

    public void setDeadlineDate() {
        //TimeZone tz = TimeZone.getDefault();
        Calendar deadlineDateCal = Calendar.getInstance();
        /*Log.d("timeZone", tz.getDisplayName());
        Log.d("Calendar",deadlineDateCal.getTimeZone().getDisplayName());
        if(tz.inDaylightTime( new Date()))
            Log.d("true", "DST is ON");
        else Log.d("false", "DST is OFF");*/
        deadlineDateCal.set(Calendar.DAY_OF_MONTH, day);
        deadlineDateCal.set(Calendar.MONTH, month-1);
        deadlineDateCal.set(Calendar.YEAR, year);
        deadlineDateCal.set(Calendar.HOUR_OF_DAY, hour);
        deadlineDateCal.set(Calendar.MINUTE, minute);
        deadlineDateCal.set(Calendar.SECOND, 0);
        deadlineDateCal.set(Calendar.MILLISECOND, 0);

        deadlineDate = deadlineDateCal.getTimeInMillis();
    }

    /**
     * Get Methods
     */

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(EventEntry.COLUMN_ID, id);
        contentValues.put(EventEntry.COLUMN_NAME, name);
        contentValues.put(EventEntry.COLUMN_DAY, day);
        contentValues.put(EventEntry.COLUMN_MONTH, month);
        contentValues.put(EventEntry.COLUMN_YEAR, year);
        contentValues.put(EventEntry.COLUMN_HOUR, hour);
        contentValues.put(EventEntry.COLUMN_MINUTE, minute);
        contentValues.put(EventEntry.COLUMN_FINISHED, isFinished);

        return contentValues;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int isFinished() {
        return isFinished;
    }

    public long getRemainingTime(){
        return remainingTime;
    }

    public long getDeadlineDate() {
        return deadlineDate;
    }

    public static ArrayList<Event> getEventsFromCursor(Cursor c){
        ArrayList<Event> result = new ArrayList<Event>();

        if(c.moveToFirst()){
            do{
                int id = c.getInt(Fields.ID.ordinal());
                String name = c.getString(Fields.NAME.ordinal());
                int day = c.getInt(Fields.DAY.ordinal());
                int month = c.getInt(Fields.MONTH.ordinal());
                int year = c.getInt(Fields.YEAR.ordinal());
                int hour = c.getInt(Fields.HOUR.ordinal());
                int minute = c.getInt(Fields.MINUTE.ordinal());

                Event event = new Event(id, name, day, month, year, hour, minute);
                result.add(event);
            }while(c.moveToNext());
        }

        return result;
    }

    /**
     * Manipulation Methods
     */

    public long calculateRemainingTime(){
        Calendar currentDate = Calendar.getInstance();
        return (deadlineDate - currentDate.getTimeInMillis());
    }

    @Override
    public int compareTo(Event another) {
        long compareDate = ((Event)another).getDeadlineDate();
        long result = this.deadlineDate - compareDate;
        if (result < 0)
            return -1;
        else if(result > 0)
            return 1;
        else return 0;
    }

    public String toStringDate(){
        Date date = new Date(deadlineDate);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dateString =  df.format(date);

        return dateString;
    }

    /* website for next method
    http://stackoverflow.com/questions/625433/how-to-convert-milliseconds-to-x-mins-x-seconds-in-java
     */
    public String toStringRemainingTime(){
        /* First Try
        Date date = new Date(remainingTime);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String dateString = df.format(date);*/

        /* Second Try
        Date date = new Date(remainingTime);
        DateFormat formatter;
        formatter = new SimpleDateFormat("HH:mm");
        String dateFormatter = formatter.format(date);*/

        String dateString;
        if( TimeUnit.MILLISECONDS.toHours(remainingTime) < 48) {
            dateString = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(remainingTime),
                    TimeUnit.MILLISECONDS.toMinutes(remainingTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(remainingTime)),
                    TimeUnit.MILLISECONDS.toSeconds(remainingTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime)));
        }
        else {
            dateString = String.format("%02dd:%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toDays(remainingTime),
                    TimeUnit.MILLISECONDS.toHours(remainingTime) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(remainingTime)),
                    TimeUnit.MILLISECONDS.toMinutes(remainingTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(remainingTime)),
                    TimeUnit.MILLISECONDS.toSeconds(remainingTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime)));
        }

        return dateString;

    }

    /*
     http://beginnersbook.com/2013/12/java-arraylist-of-object-sort-example-comparable-and-comparator/
    */
}
