package com.example.bjoern.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * Created by Björn on 14.12.2016.
 */

public class HttpIntentService extends IntentService {
    private static final String TAG = "hsflHttpIntentService";
    public HttpIntentService() {
        super("HttpIntentService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent()");
        String str = HttpReq.getResponse("http://www.surfbude.de/spots/fuerteventurasotavento.html");
        float speed = getWindSpeed(str);
        fireNotification(Float.toString(speed));
        if (speed >= 0) {
        } else {
        }
        WakefulBroadcastReceiver.completeWakefulIntent(intent);
        Log.d(TAG, "onHandleIntent(): WakeLock: is released");
    }
    // liefert die Windgeschwindigkeit in Sotavento (Fuerteventura)
    //
    // das geht auch eleganter, aber erstmal reicht es
    //
    private float getWindSpeed(String str) {
        Log.d(TAG, "getWindSpeed()");
        String search = "4 Tage Wettervorhersage"; //"Windgeschwindigkeit";
        String found = "";
        float retVal = -1;
        int start = str.indexOf(search) + search.length();
        search = "no-repeat;\"></div>";
        if (start > 0)
            start = str.indexOf(search, start) + search.length();
        search = "kn"; ///<br/>";
        int end = 0;
        if (start > 0) {
            end = str.indexOf(search, start);
            if (end > 0) {
                found = str.substring(start, end);
                try {
                    retVal = Float.parseFloat(found);
                } catch (Exception e) {
                    Log.d(TAG, "getWindSpeed(): Exception during format conversion: " + e.toString() );
                }
            }
        }
        Log.d(TAG, "getWindSpeed(): " + retVal);
        return retVal;
    }

    void fireNotification(String knots) {
        Log.d(TAG, "fireNotification(" + knots +")");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker("The ticker");
        builder.setContentTitle("Surf Wind Monitoring");
        builder.setContentText("Es geht los: " + knots + "kn");
        builder.setSmallIcon(R.drawable.ic_dialog_alert_holo_light);
        // von der Notification aus den Aufruf der Activity gewaehrleisten
        // s. https://developer.android.com/guide/topics/ui/notifiers/notifications.html#Design
        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        // und setzen
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify("direct_tag", 4711, builder.build());
    }
}