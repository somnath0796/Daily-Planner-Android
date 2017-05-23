package com.awesome.d_planner;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class AddTask extends AppCompatActivity implements View.OnClickListener{

    Button setDateButton, setTimeButton, saveButton, updateButton, deleteButton;
    TextView taskTime, taskDate;
    EditText taskName;
    RadioButton rbRegular, rbPeriodic;
    DBManager dbManager;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String T_name, T_date, T_time, T_type;
    int T_id, TaskItemId;
    Boolean newTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        //Retrieving Intent Data
        Bundle TaskMainData = this.getIntent().getExtras();
        T_id = TaskMainData.getInt("task id");
        T_name = TaskMainData.getString("task name");
        T_date = TaskMainData.getString("task date");
        T_time = TaskMainData.getString("task time");
        T_type = TaskMainData.getString("task type");
        TaskItemId = TaskMainData.getInt("task item id");
        newTask = TaskMainData.getBoolean("new task");

        dbManager = new DBManager(this, null, null, 1);

        if(TaskMainData==null){
            return;
        }

        //intialising View Widgets
        TextView taskEditLabel = (TextView)findViewById(R.id.TaskEditLabel);
        taskName  = (EditText)findViewById(R.id.taskNameVal) ;
        rbRegular = (RadioButton)findViewById(R.id.rbRegular);
        rbPeriodic = (RadioButton)findViewById(R.id.rbPeriodic);
        setDateButton = (Button)findViewById(R.id.setTaskDateButton);
        setTimeButton = (Button)findViewById(R.id.setTaskTimeButton);
        saveButton = (Button)findViewById(R.id.tSaveButton);
        updateButton = (Button)findViewById(R.id.tUpdateButton);
        deleteButton = (Button)findViewById(R.id.tDeleteButton);
        taskTime = (TextView)findViewById(R.id.tTimeVal);
        taskDate = (TextView)findViewById(R.id.tDateVal);


        if(newTask){
            taskEditLabel.setText("Add a new Task");
            saveButton.setVisibility(View.VISIBLE);
            updateButton.setVisibility(View.INVISIBLE);
        }
        else{
            taskEditLabel.setText("Update Task");
            updateButton.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.INVISIBLE);

            Toast.makeText(this,"Warning:updating will reset the Task!!",Toast.LENGTH_LONG).show();

            taskName.setText(T_name);
            if(T_type.equals("Regular")){
                rbRegular.setChecked(true);
                setDateButton.setEnabled(false);
            }
            else if(T_type.equals("Periodic")){
                rbPeriodic.setChecked(true);
                setDateButton.setEnabled(true);
            }

            taskDate.setText(T_date);
            taskTime.setText(T_time);
        }

        setDateButton.setOnClickListener(this);
        setTimeButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

        rbPeriodic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbPeriodic.isChecked()){
                    setDateButton.setEnabled(true);
                }
            }
        });
        rbRegular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbRegular.isChecked()){
                    setDateButton.setEnabled(false);
                    Toast.makeText(getApplicationContext(),"Date Disabled", Toast.LENGTH_SHORT).show();
                }
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

                            taskDate.setText(year + "-" + month + "-" + day);

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

                            taskTime.setText(hourOfDay + ":" + min);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        //saving
        if(v == saveButton){

            Tasks task = new Tasks();
            task.set_name(taskName.getText().toString());
            task.set_time(taskTime.getText().toString());

            if(rbPeriodic.isChecked()){
                task.set_date(taskDate.getText().toString());
                task.set_type("Periodic");
            }
            else if(rbRegular.isChecked()){
                task.set_date("Regular");
                task.set_type("Regular");
            }

            task.set_completion(false);
            boolean date_flag=false, time_flag=false, type_flag=false;

            //Date & time check
            if(rbPeriodic.isChecked()==false && rbRegular.isChecked()==false){
                type_flag = false;
                Toast.makeText(getApplicationContext(),"Please select a type of task", Toast.LENGTH_SHORT).show();
            }
            else{
                type_flag = true;
            }
            if(taskDate.getText().toString().equalsIgnoreCase("YYYY-MM-DD") && rbPeriodic.isChecked()){
                date_flag = false;
                Toast.makeText(getApplicationContext(),"Please select a date", Toast.LENGTH_SHORT).show();
            }
            else{
                date_flag = true;
            }
            if(taskTime.getText().toString().equalsIgnoreCase("HH : MM")){
                time_flag = false;
                Toast.makeText(getApplicationContext(),"Please select a time", Toast.LENGTH_SHORT).show();
            }
            else{
                time_flag = true;
            }
            if(type_flag && date_flag && time_flag){
                dbManager.addTask(task);
                Toast.makeText(getApplicationContext(),"Task Saved", Toast.LENGTH_SHORT).show();
                Intent out = new Intent(getApplicationContext(),MainScreen.class);
                out.putExtra("page position",1);
                out.putExtra("other", true);
                startActivity(out);
            }


        }

        //updating
        if(v == updateButton){

            Tasks task = new Tasks();
            task.set_id(T_id);
            task.set_name(taskName.getText().toString());
            task.set_time(taskTime.getText().toString());

            if(rbPeriodic.isChecked()){
                task.set_date(taskDate.getText().toString());
                task.set_type("Periodic");
            }
            else if(rbRegular.isChecked()){
                task.set_date("Regular");
                task.set_type("Regular");
            }

            task.set_completion(false);

            if(taskDate.getText().toString().equalsIgnoreCase("YYYY-MM-DD") && rbPeriodic.isChecked()){
                Toast.makeText(getApplicationContext(),"Please select a date", Toast.LENGTH_SHORT).show();
            }
            else if(taskTime.getText().toString().equalsIgnoreCase("HH:MM")){
                Toast.makeText(getApplicationContext(),"Please select a time", Toast.LENGTH_SHORT).show();
            }
            else{
                dbManager.updateTask(task);
                Toast.makeText(this.getApplicationContext(),"Task Updated", Toast.LENGTH_SHORT).show();
                Intent out = new Intent(this.getApplicationContext(),MainScreen.class);
                out.putExtra("page position",1);
                out.putExtra("other", true);
                startActivity(out);
            }



        }

        //deleting
        if(v == deleteButton){

            if(!newTask){
                dbManager.deleteTask(T_id,false);
            }


            Toast.makeText(this.getApplicationContext(),"Task Deleted", Toast.LENGTH_SHORT).show();
            Intent out = new Intent(this.getApplicationContext(),MainScreen.class);
            out.putExtra("page position",1);
            out.putExtra("other", true);
            startActivity(out);
        }
    }
}
