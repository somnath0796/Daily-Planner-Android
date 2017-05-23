package com.awesome.d_planner;

/**
 * Adapter class for Tasks row for reminders fragment
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import java.util.ArrayList;

class Reminder_ListRowAdapter extends ArrayAdapter<Reminders> {

    Reminders remindItem;

    public Reminder_ListRowAdapter(Context context, ArrayList<Reminders> remind) {
        super(context, R.layout.remind_listview, remind);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater listInflater  = LayoutInflater.from(getContext());
        View listCustomView = listInflater.inflate(R.layout.remind_listview, parent, false);

        remindItem =  getItem(position);

        TextView remindTime = (TextView) listCustomView.findViewById(R.id.rTime);
        TextView remindDate = (TextView) listCustomView.findViewById(R.id.rDate);
        TextView remindVal  = (TextView) listCustomView.findViewById(R.id.remindVal);
        ImageView remindIcon = (ImageView) listCustomView.findViewById(R.id.remindListIcon);

        remindVal.setText(remindItem.toString());
        remindTime.setText(remindItem.get_time());
        remindDate.setText(remindItem.get_date());
        remindIcon.setImageResource(R.drawable.alarm);

        return listCustomView;
    }
}
