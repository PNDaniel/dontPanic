package com.android.test.dontpanic.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.test.dontpanic.MainActivity;
import com.android.test.dontpanic.R;
import com.android.test.dontpanic.database.Event;
import com.android.test.dontpanic.database.MyDBHandler;

/**
 * Created by Daniel Nunes on 16-08-2016.
 */
public class AddDeadlineFragment extends Fragment {

    private MyDBHandler dbh;
    private Event event = new Event();
    private EditText nameText, dayText, monthText, yearText, hourText, minuteText;
    private Button saveDeadlineButton;

    public static AddDeadlineFragment newInstance(){
        return new AddDeadlineFragment();
    }

    public AddDeadlineFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_deadline_fragment, container, false);

        dbh = MyDBHandler.getInstance(getActivity());
        nameText = (EditText) rootView.findViewById(R.id.nameEvent);
        dayText = (EditText) rootView.findViewById(R.id.day);
        monthText = (EditText) rootView.findViewById(R.id.month);
        yearText = (EditText) rootView.findViewById(R.id.year);
        hourText = (EditText) rootView.findViewById(R.id.hours);
        minuteText = (EditText) rootView.findViewById(R.id.minutes);
        saveDeadlineButton = (Button) rootView.findViewById(R.id.saveDeadlineButton);

        saveDeadline();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }


    public void saveDeadline(){
        saveDeadlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToNewActivity();
            }
        });
        /*if(nameText.getText().length()>0){
            event.setName(nameText.getText().toString());
        }
        dbh.insertEvent(event);*/
    }

    private void moveToNewActivity() {
        Intent i = new Intent(getActivity(), MainActivity.class);
        long lastID;

        /*Log.d("Name",nameText.getText().toString());
        Log.d("Day",dayText.getText().toString());
        Log.d("Month",monthText.getText().toString());
        Log.d("Hour",hourText.getText().toString());
        Log.d("Minute",minuteText.getText().toString());*/

        SQLiteDatabase db = dbh.getReadableDatabase();
        String[] columns = {Event.EventEntry.COLUMN_ID, Event.EventEntry.COLUMN_NAME};
        Cursor cursor = db.query(Event.EventEntry.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.moveToLast())
        {
            Log.d("LastID if", cursor.getString(cursor.getColumnIndexOrThrow(Event.EventEntry.COLUMN_ID)));
            lastID = cursor.getLong(cursor.getColumnIndexOrThrow(Event.EventEntry.COLUMN_ID));
            lastID++;
        }
        else
        {
            lastID = 1;
            Log.d("LastID else", Long.toString(lastID));
        }

        event.setId(lastID);
        event.setName(nameText.getText().toString());
        event.setDay(Integer.parseInt(dayText.getText().toString()));
        event.setMonth(Integer.parseInt(monthText.getText().toString()));
        event.setYear(Integer.parseInt(yearText.getText().toString()));
        event.setHour(Integer.parseInt(hourText.getText().toString()));
        event.setMinute(Integer.parseInt(minuteText.getText().toString()));
        event.setFinished(0);

        dbh.insertEvent(event);
        startActivity(i);

        //((Activity) getActivity()).overridePendingTransition(0,0);
    }

}
//TODO 1: Prevent input from a previous date from inserted
//TODO 2: Scroll input for date and hour