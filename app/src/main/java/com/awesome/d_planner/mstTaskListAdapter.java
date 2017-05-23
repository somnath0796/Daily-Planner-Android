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
import java.util.ArrayList;
import android.widget.ImageView;

public class mstTaskListAdapter extends ArrayAdapter<Tasks>{

    Tasks taskItem;

    public mstTaskListAdapter(Context context, ArrayList<Tasks> tasks) {
        super(context, R.layout.mainscreen_task_list, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater listInflater = LayoutInflater.from(getContext());
        View listCustomView = listInflater.inflate(R.layout.mainscreen_task_list, parent, false);

        taskItem = getItem(position);

        ImageView mstIcon = (ImageView) listCustomView.findViewById(R.id.mstIcon);
        TextView taskTime = (TextView) listCustomView.findViewById(R.id.mstTime);
        TextView taskDate = (TextView) listCustomView.findViewById(R.id.mstDate);
        TextView taskName = (TextView) listCustomView.findViewById(R.id.mstName);

        taskName.setText(taskItem.toString());
        taskTime.setText(taskItem.get_time());
        taskDate.setText(taskItem.get_date());
        mstIcon.setImageResource(R.drawable.taskfile);



        return listCustomView;
    }
}
