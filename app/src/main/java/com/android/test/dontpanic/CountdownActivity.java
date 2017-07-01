package com.android.test.dontpanic;

import android.app.Activity;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.test.dontpanic.database.Event;
import com.android.test.dontpanic.database.MyDBHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

/**
 * Created by Daniel Nunes on 16-07-2016.
 */
public class CountdownActivity extends Activity {

    private ArrayList<Integer> images = new ArrayList<>();
    private int currentImage = 0;

    private int id;
    private long deadlineDate;
    private String name;
    private MyDBHandler dbh;

    private int day = 0;
    private int month = 0;
    private int hours = 0;
    private int minutes = 0;
    private static final String FORMAT = "%02d:%02d:%02d";

    TextView countdownValue;
    Long countdownNumber = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdown);

        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();

        countdownValue = (TextView) findViewById(R.id.countdownValue);

        if (!extrasBundle.isEmpty()) {
            id = extrasBundle.getInt("id");
            deadlineDate = extrasBundle.getLong("date");
            name = extrasBundle.getString("name");

            /*day = extrasBundle.getInt("day");
            month = extrasBundle.getInt("month");
            hours = extrasBundle.getInt("hours");
            minutes = extrasBundle.getInt("minutes");*/

            //images = extrasBundle.getIntegerArrayList("images");

            /* Testing
            day = 21;
            month = 07;
            hours = 23;
            minutes = 59;*/

            /*Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DATE, day);
            cal.set(Calendar.MONTH, month - 1);
            cal.set(Calendar.HOUR_OF_DAY, hours);
            cal.set(Calendar.MINUTE, minutes);
            cal.set(Calendar.SECOND, 0);
            Date date = cal.getTime();*/

            //DateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm:ss");
            //String strDate = dateFormat.format(date);

            /*
            // http://stackoverflow.com/questions/5175728/how-to-get-the-current-date-time-in-java
            TimeZone tz = TimeZone.getDefault();
            Log.d("Offset", String.valueOf(tz.getOffset(date.getTime())));
            If I need Timezones and DST options

            Log.d("Input date", strDate);
            Date date1 = new Date();
            Log.d("Date Current", dateFormat.format(date1)); // 20/07 10:50:26
            */

            //countdownNumber = date.getTime() - System.currentTimeMillis();
            countdownNumber = deadlineDate - System.currentTimeMillis();
        }

        new CountDownTimer(countdownNumber, 1000) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {
                countdownValue.setText("" + String.format(FORMAT, TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }
            public void onFinish() {
                countdownValue.setText("Freeeeee!");
            }
        }.start();
        //setInitialImage();
        //setImageRotateListener();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        dbh = MyDBHandler.getInstance(this);
        Event event = dbh.getEvent(id);

    }

    private void setImageRotateListener() {
        final Button rotatebutton = (Button) findViewById(R.id.makeItBetter);
        final ImageView imageDisplay = (ImageView) findViewById(R.id.imageDisplay);
        rotatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                imageDisplay.setVisibility(View.VISIBLE);
                currentImage++;
                if (currentImage == images.size()) {
                    currentImage = 0;
                }
                setCurrentImage();
                imageDisplay.postDelayed(new Runnable(){
                    public void run(){ imageDisplay.setVisibility(View.GONE);}
                },4000);
            }
        });

    }

    private void setInitialImage() {
        setCurrentImage();
    }

    private void setCurrentImage() {

        final ImageView imageView = (ImageView) findViewById(R.id.imageDisplay);
        imageView.setImageResource(images.get(currentImage));
    }
}
