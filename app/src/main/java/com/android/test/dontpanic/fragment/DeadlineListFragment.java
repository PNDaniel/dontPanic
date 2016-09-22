package com.android.test.dontpanic.fragment;

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

import java.util.ArrayList;

/**
 * Created by Daniel Nunes on 12-09-2016.
 */
public class DeadlineListFragment extends Fragment {

    private MyDBHandler dbh;
    private ArrayList<Event> events = new ArrayList<>();

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
        EventAdapter adapter = new EventAdapter(rootView.getContext(), events);

        rvEvents.setAdapter(adapter);
        rvEvents.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

}