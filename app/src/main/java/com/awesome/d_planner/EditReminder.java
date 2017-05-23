package com.awesome.d_planner;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class EditReminder extends AppCompatActivity implements View.OnClickListener{

    Button setDateButton, setTimeButton, rsaveButton, rupdateButton, rdeleteButton;
    TextView remindTime, remindDate;
    EditText remindValue;
    DBManager dbManager;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String R_Val, R_date, R_time;
    int R_id, RemindItemId;
    Boolean newRemind;
    PendingIntent alarmpintent;
    AlarmManager alarm_manager;
    Intent alarmintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);

        //Retrieving Intent Data
        Bundle RemindMainData = this.getIntent().getExtras();
        R_id = RemindMainData.getInt("remind id");
        R_Val = RemindMainData.getString("remind val");
        R_date = RemindMainData.getString("remind date");
        R_time = RemindMainData.getString("remind time");
        RemindItemId = RemindMainData.getInt("remind item id");
        newRemind = RemindMainData.getBoolean("new remind");

        dbManager = new DBManager(this, null, null, 1);

        if(RemindMainData==null){
            return;
        }

        //intializing View Widgets
        TextView remindEditLabel = (TextView)findViewById(R.id.RemindEditLabel);
        remindValue = (EditText)findViewById(R.id.remindValue) ;
        setDateButton = (Button)findViewById(R.id.rSetDateButton);
        setTimeButton = (Button)findViewById(R.id.rSetTimeButton);
        rsaveButton = (Button)findViewById(R.id.rSaveButton);
        rupdateButton = (Button)findViewById(R.id.rUpdateButton);
        rdeleteButton = (Button)findViewById(R.id.rDeleteButton);
        remindTime = (TextView)findViewById(R.id.rTimeVal);
        remindDate = (TextView)findViewById(R.id.rDateVal);

        alarm_manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmintent = new Intent(this, Alarm_Reciever.class);

        if(newRemind){
            remindEditLabel.setText("Add a new Reminder");
            rsaveButton.setVisibility(View.VISIBLE);
            rupdateButton.setVisibility(View.INVISIBLE);
        }
        else{
            remindEditLabel.setText("Update Reminder");
            rupdateButton.setVisibility(View.VISIBLE);
            rsaveButton.setVisibility(View.INVISIBLE);


            remindValue.setText(R_Val);
            remindDate.setText(R_date);
            remindTime.setText(R_time);
        }

        setDateButton.setOnClickListener(this);
        setTimeButton.setOnClickListener(this);
        rsaveButton.setOnClickListener(this);
        rupdateButton.setOnClickListener(this);
        rdeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!newRemind){
                    dbManager.deleteReminders(R_id,false);
                }


                Toast.makeText(getApplicationContext(),"Reminder Erased", Toast.LENGTH_SHORT).show();
                Intent out = new Intent(getApplicationContext(),MainScreen.class);
                out.putExtra("page position",2);
                out.putExtra("other", true);
                startActivity(out);
            }
        });
    }

    @Override
    public void onClick(View v) {
        dbManager = new DBManager(this, null, null, 1);
        if (v == setDateButton) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            String month, day;
                            if((monthOfYear+1)<10){
                                month = "0"+(monthOfYear+1);
                            }
                            else{
                                month = Integer.toString((monthOfYear+1));
                            }
                            if((dayOfMonth)<10){
                                day = "0"+dayOfMonth;
                            }
                            else{
                                day = Integer.toString(dayOfMonth);
                            }

                            remindDate.setText(year + "-" + month + "-" + day);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == setTimeButton) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String min;
                            if(minute<10)
                                min = "0"+minute;
                            else
                                min = Integer.toString(minute);

                            remindTime.setText(hourOfDay + ":" + min);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        //saving
        if(v == rsaveButton){
            Reminders reminder = new Reminders();
            reminder.set_val(remindValue.getText().toString());
            reminder.set_time(remindTime.getText().toString());
            reminder.set_date(remindDate.getText().toString());



            boolean date_flag=false, time_flag=false;

            if(remindDate.getText().toString().equalsIgnoreCase("YYYY-MM-DD")){
                date_flag = false;
                Toast.makeText(getApplicationContext(),"Please select a date", Toast.LENGTH_SHORT).show();
            }
            else{
                date_flag = true;
            }
            if(remindTime.getText().toString().equalsIgnoreCase("HH : MM")){
                time_flag = false;
                Toast.makeText(getApplicationContext(),"Please select a time", Toast.LENGTH_SHORT).show();
            }
            else{
                time_flag = true;
            }
            if(date_flag && time_flag){

                dbManager.addReminder(reminder);
                alarmintent.putExtra("Rem_note", reminder.get_val());
                Calendar c = getTime(reminder.get_time());
                alarmpintent = PendingIntent.getBroadcast(this,0,alarmintent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarm_manager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),alarmpintent);
                Toast.makeText(getApplicationContext(),"Reminder Set", Toast.LENGTH_SHORT).show();
                Intent out = new Intent(getApplicationContext(),MainScreen.class);
                out.putExtra("page position",2);
                out.putExtra("other", true);
                startActivity(out);
            }
        }

        //updating
        if(v == rupdateButton){
            Reminders reminder = new Reminders();
            reminder.set_val(remindValue.getText().toString());
            reminder.set_time(remindTime.getText().toString());
            reminder.set_date(remindDate.getText().toString());

            boolean date_flag=false, time_flag=false;

            if(remindDate.getText().toString().equalsIgnoreCase("YYYY-MM-DD")){
                date_flag = false;
                Toast.makeText(getApplicationContext(),"Please select a date", Toast.LENGTH_SHORT).show();
            }
            else{
                date_flag = true;
            }
            if(remindTime.getText().toString().equalsIgnoreCase("HH : MM")){
                time_flag = false;
                Toast.makeText(getApplicationContext(),"Please select a time", Toast.LENGTH_SHORT).show();
            }
            else{
                time_flag = true;
            }
            if(date_flag && time_flag){
                dbManager.updateReminder(reminder);
                Toast.makeText(getApplicationContext(),"Reminder Updated", Toast.LENGTH_SHORT).show();
                Intent out = new Intent(getApplicationContext(),MainScreen.class);
                //out.putExtra("page position",2);
                out.putExtra("other", true);
                startActivity(out);

            }

        }
    }

    private Calendar getTime(String Time){

        int p = Time.indexOf(":");
        int hr = Integer.parseInt(Time.substring(0,p));
        int min = Integer.parseInt(Time.substring(p+1, Time.length()));
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, hr);
        now.set(Calendar.MINUTE, min);
        Log.i("In EditReminder:","Setting Time"+now.getTime());
        return now;
    }


}
