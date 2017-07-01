package com.android.test.dontpanic;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.test.dontpanic.fragment.AddDeadlineFragment;
import com.android.test.dontpanic.fragment.DeadlineListFragment;

import java.util.ArrayList;

/*
* Each deadline will have a time difference between the above one
* example:
* Teste SDIS 78h48m
* 29/06 16h       0
* Entrega de COMP 30/06 14h30
* 91h18 +22h30m
* */

public class MainActivity extends FragmentActivity {

    //private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private RelativeLayout mDrawerList2;
    private ImageButton imgButton;

    //private Integer images[]= {R.drawable.cat1};
    /*private ArrayList<Integer> list = new ArrayList<Integer>() {{
       add(R.drawable.cat1); }};
       This method creates a subclass of ArrayList which is has an instance initializer and that class
       is created just to create one object
       */
    private ArrayList<Integer> images= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //setContentView(R.layout.main_drawer);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.main);

        imgButton = (ImageButton) findViewById(R.id.show_AddDeadline);

        images.add(R.drawable.cat1);
        images.add(R.drawable.cat2);

        /*if(savedInstanceState == null){
            AddDeadlineFragment adf = AddDeadlineFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.main_layout,adf).commit();
        }*/

        //mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);

       /* mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList2 = (RelativeLayout) findViewById(R.id.left_drawer2);
        imgButton = (ImageButton) findViewById(R.id.drawer_button );*/

        if(savedInstanceState == null) {
            DeadlineListFragment dL = DeadlineListFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.deadlineList, dL).addToBackStack("deadline List").commit();

            imgButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left);

                    AddDeadlineFragment adf = AddDeadlineFragment.newInstance();
                    ft.replace(R.id.main_layout,adf, "add Deadline Fragment").addToBackStack("add Deadline Fragment");
                    ft.commit();

                    //getSupportFragmentManager().beginTransaction().add(R.id.main_layout, adf).commit();
                }
            });
        }
    }

    /*
        Experiment with:
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        OR
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
     */

    // http://javapapers.com/android/get-user-input-in-android/
    // http://stackoverflow.com/questions/4531396/get-value-of-a-edit-text-field
    // http://stackoverflow.com/questions/30782330/numberformatexception-invalid-int
    // http://stackoverflow.com/questions/17206273/edit-text-shows-exception-invalid-int

    /*public void goToCountdownActivity(View view){
        Intent intent = new Intent(this, CountdownActivity.class);
        Bundle bundle = new Bundle();

        EditText dayText = (EditText) findViewById(R.id.day);
        EditText monthText = (EditText) findViewById(R.id.month);
        EditText hoursText = (EditText) findViewById(R.id.hours);
        EditText minutesText = (EditText) findViewById(R.id.minutes);

        bundle.putInt("day", Integer.parseInt(dayText.getText().toString()));
        bundle.putInt("month", Integer.parseInt(monthText.getText().toString()));
        bundle.putInt("hours", Integer.parseInt(hoursText.getText().toString()));
        bundle.putInt("minutes", Integer.parseInt(minutesText.getText().toString()));
        bundle.putIntegerArrayList("images",images);

        intent.putExtras(bundle);
        startActivity(intent);
    }*/
}