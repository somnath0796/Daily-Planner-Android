package com.awesome.d_planner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.awesome.d_planner.*;
import java.util.ArrayList;

public class TaskFragment extends Fragment{
    DBManager dbManager;
    ListAdapter tasksAdapter;

    public TaskFragment() {

    }

    public static TaskFragment newInstance(){
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tasks_fragment, container, false);

        dbManager = new DBManager(getContext(),null,null,1);
        int n = dbManager.getTaskRows();
        Toast.makeText(getContext(), "main:"+n,Toast.LENGTH_SHORT).show();


        ListView tasksList = (ListView)view.findViewById(R.id.tasklist);
        FloatingActionButton addTask = (FloatingActionButton)view.findViewById(R.id.addTaskButton);
        final boolean[] addTaskButtonPressed = {false};

        //addTask action Performed to go to Add Task Activity
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity act = getActivity();
                addTaskButtonPressed[0] = true;
                Intent i = new Intent(act, AddTask.class);
                i.putExtra("new task",addTaskButtonPressed[0]);


                startActivity(i);
            }
        });



        ArrayList<String> taskTitles = getTaskList();
        ArrayList<Tasks> dummy = new ArrayList<Tasks>();
        dummy.add(new Tasks("No Tasks", null,null,null));
        ArrayList<Tasks> tasklist = dbManager.getCurrentTasks();

        if(taskTitles.isEmpty()){
            tasksAdapter = new Task_ListrowAdapter(getContext(), dummy);
        }
        else{
            tasksAdapter = new Task_ListrowAdapter(getContext(),tasklist);
        }


        tasksList.setAdapter(tasksAdapter);
        tasksList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            Tasks t;
            DBManager db;
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String task = null;
                task = String.valueOf(parent.getItemAtPosition(position));
                t = (Tasks)tasksAdapter.getItem(position);
                db = new DBManager(getContext(),null,null,1);
                Intent in = new Intent(getContext(),AddTask.class);
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
                return true;
            }
        });
        tasksList.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){

                    CheckBox cb;
                    Tasks t;
                    DBManager db;

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String task = null;
                        task = String.valueOf(parent.getItemAtPosition(position));
                        t = (Tasks)tasksAdapter.getItem(position);
                        db = new DBManager(getContext(),null,null,1);
                        TasksViewHolder viewHolder = (TasksViewHolder)view.getTag();
                        //CheckBox cb1 = viewHolder.getCheckBox();

                        cb = (CheckBox)view.findViewById(R.id.taskCheck);
                        cb.setChecked(t.isChecked());

                        Toast.makeText(getContext(), "test1",Toast.LENGTH_SHORT).show();
                        if(cb.isChecked()){
                            t.set_completion(true);
                        }
                        else{
                            t.set_completion(false);
                        }
                        db.updateTask(t);

                        Toast.makeText(getContext(), "test2",Toast.LENGTH_SHORT).show();



                    }
                }
        );
        return view;
    }

    public ArrayList<String> getTaskList(){
        dbManager = new DBManager(getContext(),null,null,1);

        ArrayList<Tasks> task = dbManager.getCurrentTasks();
        int taskc = dbManager.getTaskCompleted();
        ArrayList<String> title = new ArrayList<String>();

        for(Tasks t : task){
            title.add(t.toString());
        }

        return title;


    }

    private static class TasksViewHolder{
        private CheckBox checkBox ;
        private TextView title ;
        private TextView date ;
        private TextView time ;
        public TasksViewHolder() {}
        public TasksViewHolder(TextView title, TextView date, TextView time, CheckBox checkBox ) {
            this.checkBox = checkBox ;
            this.title = title ;
            this.date = date ;
            this.time = time ;
        }
        public CheckBox getCheckBox() {
            return checkBox;
        }
        public void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }


    }
}
