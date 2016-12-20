package com.example.bjoern.service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by Björn on 14.12.2016.
 */

public class MyReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("aufGehts")){
            Intent serviceIntent = new Intent(context, HttpIntentService.class);
            startWakefulService(context,serviceIntent);
        }
    }
}
