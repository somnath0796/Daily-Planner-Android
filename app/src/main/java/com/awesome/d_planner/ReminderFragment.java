package com.awesome.d_planner;



import android.app.Activity;
import android.app.AlarmManager;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.awesome.d_planner.*;
import java.util.ArrayList;


public class ReminderFragment extends Fragment{
    DBManager dbManager;


    public ReminderFragment() {

    }

    public static ReminderFragment newInstance(){
        ReminderFragment fragment = new ReminderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reminders_fragment, container, false);

        dbManager = new DBManager(getContext(),null,null,1);

        ListView remindList = (ListView)view.findViewById(R.id.remindlist);
        FloatingActionButton addRemind = (FloatingActionButton)view.findViewById(R.id.addRemindButton);
        final boolean[] addRemindButtonPressed = {false};

        //AddReminder action Performed to go to Add Reminder Activity
        addRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity act = getActivity();
                addRemindButtonPressed[0] = true;
                Intent i = new Intent(act, EditReminder.class);
                i.putExtra("new remind",addRemindButtonPressed[0]);

                startActivity(i);
            }
        });

        ListAdapter remindsAdapter;

        ArrayList<String> remindTitles = getRemindList();
        ArrayList<Reminders> dummy = new ArrayList<Reminders>();
        dummy.add(new Reminders("No Reminders", null,null));

        ArrayList<Reminders> remindlist = dbManager.getReminders();

        if(remindTitles.isEmpty()){
            remindsAdapter = new Reminder_ListRowAdapter(getContext(), dummy);
        }
        else{
            remindsAdapter = new Reminder_ListRowAdapter(getContext(),remindlist);
        }


        remindList.setAdapter(remindsAdapter);

        remindList.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent in = new Intent(getContext(),EditReminder.class);
                        String reminder = null;
                        reminder = String.valueOf(parent.getItemAtPosition(position));
                        Reminders r = (Reminders)parent.getItemAtPosition(position);
                        int R_id = r.get_id();
                        if(reminder.equalsIgnoreCase("No Reminders")){
                            in.putExtra("new remind",true);
                        }
                        in.putExtra("remind id", R_id);
                        in.putExtra("remind val", r.get_val());
                        in.putExtra("remind date",r.get_date());
                        in.putExtra("remind time", r.get_time());
                        in.putExtra("remind item id", position);

                        startActivity(in);

                    }
                }
        );

        return view;
    }

    public ArrayList<String> getRemindList(){
        dbManager = new DBManager(getContext(),null,null,1);

        ArrayList<Reminders> remind = dbManager.getReminders();
        ArrayList<String> title = new ArrayList<String>();

        for(Reminders r : remind){
            title.add(r.toString());
        }

        return title;


    }
}
