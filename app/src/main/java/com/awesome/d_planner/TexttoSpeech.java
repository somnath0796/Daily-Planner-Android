package com.awesome.d_planner;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;




public class TexttoSpeech extends Service {
    @Nullable
    String seek = "MyText";
    TextToSpeech t1;
    String msg;

    public IBinder onBind(Intent intent) {
        msg = intent.getExtras().getString("Rem_note");
        Log.i("In Data passed",msg);
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override


    public void onDestroy() {

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {

                public void onInit(int status) {
                    int result=0;
                    if(status != TextToSpeech.ERROR) {
                        result = t1.setLanguage(Locale.UK);
                    }
                    if (status == TextToSpeech.SUCCESS) {
                        speakOut(msg);

                    }
                    else{
                        Log.e("TTS", "Initilization Failed!");
                    }
                }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {


            public void onInit(int status) {
                int result=0;
                if(status != TextToSpeech.ERROR) {
                    result = t1.setLanguage(Locale.UK);
                }
                if (status == TextToSpeech.SUCCESS) {
                    speakOut(msg);

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }
        });



        return START_NOT_STICKY;
    }

    private void speakOut(String data) {
        Log.i("in TexttoSpeech","::in speakout");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            t1.speak(data, t1.QUEUE_FLUSH, null, seek + "jj");
        Toast.makeText(this.getApplicationContext(),data,Toast.LENGTH_SHORT).show();
    }
}

