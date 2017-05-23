package com.awesome.d_planner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Alarm_Reciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {



        //create and intent to the ringtone service
        Intent service_intent = new Intent(context,TexttoSpeech.class);
        Bundle d = intent.getExtras();
        String msg = d.getString("Rem_note");
        Log.i("in Alarm_Reciever::","onReceive");
        service_intent.putExtra("Rem_note",msg);
        //start service

        context.startService(service_intent);
    }
}
