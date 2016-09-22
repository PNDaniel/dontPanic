package com.android.test.dontpanic.fragment;

import com.android.test.dontpanic.database.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Daniel Nunes on 16-09-2016.
 */
public class Sorter {

    public static void sortByDeadline(ArrayList<Event> events){
        Collections.sort(events);
    }
}
