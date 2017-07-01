package com.android.test.dontpanic.fragment;

import android.app.Activity;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.test.dontpanic.EventAdapter;
import com.android.test.dontpanic.R;
import com.android.test.dontpanic.database.Event;
import com.android.test.dontpanic.database.MyDBHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;

/**
 * Created by Daniel Nunes on 12-09-2016.
 */
public class DeadlineListFragment extends Fragment {

    private MyDBHandler dbh;
    private Handler timeHandler = new Handler();
    private ArrayList<Event> events = new ArrayList<>();
    private EventAdapter adapter;

    public static DeadlineListFragment newInstance(){
        return new DeadlineListFragment();
    }

    public DeadlineListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.deadline_list_fragment, container, false);

        dbh = MyDBHandler.getInstance(rootView.getContext());
        events = dbh.getAllEvents();
        for(Event event:dbh.getAllEvents()){
            Log.i("TIME",String.valueOf(event.getRemainingTime()));
        }

        RecyclerView rvEvents = (RecyclerView) rootView.findViewById(R.id.deadlineListRecycler);
        adapter = new EventAdapter(rootView.getContext(), events);

        rvEvents.setAdapter(adapter);

        // incluir o adapter.notifyDataSetChanged()
        Thread t = new Thread(){
        @Override
        public void run(){
            try{
                while (!isInterrupted()){
                    Thread.sleep(1000);
                    ((Activity)getContext()).runOnUiThread(new Runnable(){
                        @Override
                                public void run(){
                                 updateTextView(events.size());
                        }
                    });
                }
            }
            catch (InterruptedException e){ }
            }
        };
        t.start();

        rvEvents.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        return rootView;
    }

    private void updateTextView(int eventsSize){
            adapter.notifyItemRangeChanged(0, eventsSize);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

}