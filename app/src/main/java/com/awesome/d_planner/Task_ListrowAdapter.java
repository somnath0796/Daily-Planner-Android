package com.awesome.d_planner;

/**
 * Adapter class for Tasks row for task fragment
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.CheckBox;
import java.util.ArrayList;

class Task_ListrowAdapter extends ArrayAdapter<Tasks> {

    Tasks taskItem;
    CheckBox taskCheck;

    public Task_ListrowAdapter(Context context, ArrayList<Tasks> tasks) {
        super(context, R.layout.task_listrow, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater listInflater  = LayoutInflater.from(getContext());
        View listCustomView = listInflater.inflate(R.layout.task_listrow, parent, false);

        taskItem =  getItem(position);
        taskCheck = (CheckBox)listCustomView.findViewById(R.id.taskCheck);
        TextView taskTime = (TextView) listCustomView.findViewById(R.id.tTime);
        TextView taskDate = (TextView) listCustomView.findViewById(R.id.tDate);
        TextView taskName  = (TextView) listCustomView.findViewById(R.id.taskName);

        taskName.setText(taskItem.toString());
        taskTime.setText(taskItem.get_time());
        taskDate.setText(taskItem.get_date());


        if(taskItem.get_completion()==0)
            taskCheck.setChecked(false);
        else
            taskCheck.setChecked(true);


        return listCustomView;
    }

}
