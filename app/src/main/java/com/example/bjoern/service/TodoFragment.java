package com.example.bjoern.service;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import fllog.Log;

/**
 * Created by Björn on 14.12.2016.
 */

public class TodoFragment extends Fragment {

    private static final String TAG = "TodoFragment";
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button butDoIt;
        Log.d(TAG, "onCreateView():");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todo, container, false);

        butDoIt = (Button)view.findViewById(R.id.butDoIt);

        // Listener
        butDoIt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startAlarmManager();
            }
        });

        return view;
    }

    public void startAlarmManager(){
        Log.d(TAG, "Start Alarm");

        alarmMgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent("aufGehts");
        alarmIntent = PendingIntent.getBroadcast(getActivity(),0,intent,0);

        long startTime = System.currentTimeMillis();
        long repeatDuration = 1*60*1000;

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,startTime,repeatDuration,alarmIntent);
    }
}
