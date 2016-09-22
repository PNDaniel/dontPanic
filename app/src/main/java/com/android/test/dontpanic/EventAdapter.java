package com.android.test.dontpanic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.test.dontpanic.database.Event;

import java.util.ArrayList;

/**
 * Created by Daniel Nunes on 12-09-2016.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView eventName;
        public TextView eventTime;
        public TextView eventDate;
        public TextView eventTimeDiff;

        public ViewHolder(View itemView){
            super(itemView);

            eventName = (TextView) itemView.findViewById(R.id.eventName);
            eventTime = (TextView) itemView.findViewById(R.id.eventTime);
            eventDate = (TextView) itemView.findViewById(R.id.eventDate);
            eventTimeDiff = (TextView) itemView.findViewById(R.id.timeDifference);
        }
    }

    private ArrayList<Event> events;
    private Context context;

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
        ViewHolder viewHolder = new ViewHolder(eventView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventAdapter.ViewHolder viewHolder, int position){
        Event event = events.get(position);

        TextView eventName = viewHolder.eventName;
        eventName.setText(event.getName());

        TextView eventTime = viewHolder.eventTime;
        //eventTime.setText(String.valueOf(event.getRemainingTime()));
        eventTime.setText(event.toStringRemainingTime());

        TextView eventDate = viewHolder.eventDate;
        eventDate.setText(event.toStringDate());

        TextView eventTimeDiff = viewHolder.eventTimeDiff;
        //needs to calculate difference between the above deadline
        //eventTimeDiff.setText(event.get);
    }

    @Override
    public int getItemCount(){
        return events.size();
    }

}
