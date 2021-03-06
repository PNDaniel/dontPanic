package com.android.test.dontpanic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.test.dontpanic.database.Event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.RunnableFuture;

/**
 * Created by Daniel Nunes on 12-09-2016.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{
    private static ArrayList<Event> events;
    private Context context;
    private TextView eventName, eventTime, eventDate, eventTimeDiff;
    private Handler timeHandler = new Handler();

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, Serializable{
        public TextView eventName;
        public TextView eventTime;
        public TextView eventDate;
        public TextView eventTimeDiff;

        private Context context;

        /*public ViewHolder(View itemView){
            super(itemView);

            eventName = (TextView) itemView.findViewById(R.id.eventName);
            eventTime = (TextView) itemView.findViewById(R.id.eventTime);
            eventDate = (TextView) itemView.findViewById(R.id.eventDate);
            eventTimeDiff = (TextView) itemView.findViewById(R.id.timeDifference);
        }*/

        public ViewHolder(Context context, View itemView){
            super(itemView);
            this.eventName = (TextView) itemView.findViewById(R.id.eventName);
            this.eventTime = (TextView) itemView.findViewById(R.id.eventTime);
            this.eventDate = (TextView) itemView.findViewById(R.id.eventDate);
            this.eventTimeDiff = (TextView) itemView.findViewById(R.id.timeDifference);

            this.context = context;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                Event event = events.get(position);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(context, CountdownActivity.class);

                bundle.putInt("id", event.getId());
                bundle.putString("name", event.getName());
                bundle.putLong("date", event.getDeadlineDate());

                /*bundle.putInt("day", event.getDay());
                bundle.putInt("month", event.getMonth());
                bundle.putInt("year", event.getYear());
                bundle.putInt("hours",  event.getHour());
                bundle.putInt("minutes",  event.getMinute());*/

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        }
    }



    public EventAdapter(Context context, ArrayList<Event> events){
        this.events = events;
        this.context = context;
    }

    private Context getContext(){
        return context;
    }

    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context mcontext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mcontext);

        View eventView = inflater.inflate(R.layout.item_event, parent, false);
        ViewHolder viewHolder = new ViewHolder(mcontext,eventView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventAdapter.ViewHolder viewHolder, int position){
        Event event = events.get(position);

        eventName = viewHolder.eventName;
        eventName.setText(event.getName());

        eventTime = viewHolder.eventTime;
        //eventTime.setText(String.valueOf(event.getRemainingTime()));
        eventTime.setText(event.toStringRemainingTime());

        eventDate = viewHolder.eventDate;
        eventDate.setText(event.toStringDate());

        eventTimeDiff = viewHolder.eventTimeDiff;
        //needs to calculate difference between the above deadline
        //eventTimeDiff.setText(event.get);
    }

    @Override
    public int getItemCount(){
        return events.size();
    }

    /*private Runnable updateTime = new Runnable() {
        @Override
        public void run() {
            long currentTime = SystemClock.uptimeMillis();

            timeHandler.postDelayed(this, 0);
        }
    };*/

}
