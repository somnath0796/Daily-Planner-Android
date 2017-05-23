package com.awesome.d_planner;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Calendar;


public class MainScreen extends AppCompatActivity {

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    int pos;
    boolean other = false;
    DBManager dbManager;
    AlarmManager alarm_manager;
    PendingIntent alarmpintent;
    Intent alarmintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

/*
        dbManager = new DBManager(this,null,null,1);
        alarm_manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmintent = new Intent(this, Alarm_Reciever.class);

        ArrayList<Reminders> rem = dbManager.getCurrentReminders();
        for(Reminders r:rem){
            Calendar calendar = dbManager.remTime(r);
            alarmintent.putExtra("Rem_note", r.get_val());
            Log.i("in remTime now:",Calendar.getInstance().toString());
            alarmpintent = PendingIntent.getBroadcast(MainScreen.this,0,alarmintent, PendingIntent.FLAG_UPDATE_CURRENT);
                Toast.makeText(this, ""+Calendar.getInstance().getTimeInMillis()+" t "+calendar.getTimeInMillis(),Toast.LENGTH_LONG).show();
                alarm_manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),alarmpintent);
        }

*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        /*
        Bundle MainScreenData = this.getIntent().getExtras();
        if(MainScreenData==null)
            return;
        else{
            pos = MainScreenData.getInt("page position");
            other = MainScreenData.getBoolean("other page");
        }*/

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());//,pos, other);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);





        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent in = new Intent(this, SettingsActivity.class);
            startActivity(in);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MainScreen Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.awesome.d_planner/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();


        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MainScreen Page", // TODO: Define a title for the content shown.
                //TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.awesome.d_planner/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    public static class MainScreenFragment extends Fragment {
        DBManager dbManager;

        public MainScreenFragment() {
        }

        public static MainScreenFragment newInstance() {
            MainScreenFragment fragment = new MainScreenFragment();
            Bundle args = new Bundle();

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_main_screen, container, false);
            ListView tList = (ListView)rootView.findViewById(R.id.task_list);
            ListView rList = (ListView)rootView.findViewById(R.id.remind_list);
            ProgressBar pEfficient = (ProgressBar) rootView.findViewById(R.id.progressEfficient);

            dbManager = new DBManager(getContext(),null,null,1);


            ListAdapter tasksAdapter;
            ArrayList<String> taskTitles = getTaskList();
            ArrayList<Tasks> tdummy = new ArrayList<Tasks>();
            tdummy.add(new Tasks("No Tasks", null,null,null));
            ArrayList<Tasks> tasklist = dbManager.getCurrentTasks();

            if(taskTitles.isEmpty()){
                tasksAdapter = new mstTaskListAdapter(getContext(), tdummy);
            }
            else{
                tasksAdapter = new mstTaskListAdapter(getContext(), tasklist);
            }


            tList.setAdapter(tasksAdapter);

            tList.setOnItemClickListener(
                    new AdapterView.OnItemClickListener(){

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent in = new Intent(getContext(),AddTask.class);
                            String task = null;
                            task = String.valueOf(parent.getItemAtPosition(position));
                            Tasks t = (Tasks)parent.getItemAtPosition(position);
                            int T_id = t.get_id();
                            if(task.equalsIgnoreCase("No Tasks")){
                                in.putExtra("new task",true);
                            }
                            in.putExtra("task id", T_id);
                            in.putExtra("task name", t.get_name());
                            in.putExtra("task date",t.get_date());
                            in.putExtra("task time", t.get_time());
                            in.putExtra("task type", t.get_type());
                            in.putExtra("task item id", position);

                            startActivity(in);

                        }
                    }
            );

            pEfficient.setProgress(getEfficiency());

            ListAdapter remindsAdapter;

            ArrayList<String> remindTitles = getRemindList();
            ArrayList<Reminders> rdummy = new ArrayList<Reminders>();
            rdummy.add(new Reminders("No Reminders", null,null));

            ArrayList<Reminders> remindlist = dbManager.getReminders();

            if(remindTitles.isEmpty()){
                remindsAdapter = new Reminder_ListRowAdapter(getContext(), rdummy);
            }
            else{
                remindsAdapter = new Reminder_ListRowAdapter(getContext(),remindlist);
            }


            rList.setAdapter(remindsAdapter);

            rList.setOnItemClickListener(
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




            return rootView;

        }
        public ArrayList<String> getTaskList(){
            dbManager = new DBManager(getContext(),null,null,1);

            ArrayList<Tasks> task = dbManager.getCurrentTasks();
            ArrayList<String> title = new ArrayList<String>();

            for(Tasks t : task){
                title.add(t.toString());
            }

            return title;
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

        public int getEfficiency(){
            dbManager = new DBManager(getContext(),null,null,1);
            ArrayList<Tasks> task = dbManager.getCurrentTasks();
            int total = task.size();
            int comp = dbManager.getTaskCompleted();
            Log.i("Planner Efficiency::","total:"+total+" comp:"+comp);
            int efficiency=0;
                try{
                    efficiency=(comp/total)*100;
                }catch (Exception e){

                }
            return efficiency;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm){
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {

            Fragment frag[] = new Fragment[6];
                frag[0] = MainScreenFragment.newInstance();
                frag[1] = TaskFragment.newInstance();
                frag[2] = ReminderFragment.newInstance();
                frag[3] = NoteFragment.newInstance();

            switch(position){
                case 0: return frag[0];
                case 1: return frag[1];
                case 2: return frag[2];
                case 3: return frag[3];

                default: return null;
            }


        }

        @Override
        public int getCount() {
            // returns the count of tabs
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Home";
                case 1:
                    return "Tasks";
                case 2:
                    return "Reminders";
                case 3:
                    return "Notes";
            }
            return null;
        }
    }
}
