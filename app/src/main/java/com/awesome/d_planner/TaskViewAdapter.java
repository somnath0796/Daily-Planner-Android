package com.awesome.d_planner;

/**
 * Adapter Class for Custom TaskView Adapter for Rows
 */
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import java.util.ArrayList;

public class TaskViewAdapter extends ArrayAdapter<String>{
    public TaskViewAdapter(Context context, ArrayList<String> str) {
        super(context, R.layout.mainscreen_task_list,str);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater listInflater  = LayoutInflater.from(getContext());
        View taskCustomView = listInflater.inflate(R.layout.mainscreen_task_list, parent, false);

        return taskCustomView;
    }
}
