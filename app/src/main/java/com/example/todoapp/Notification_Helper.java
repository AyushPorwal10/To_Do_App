package com.example.todoapp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notification_Helper {
    public static void createNotification(Context context) {
        Intent intent = new Intent(context,MainActivity.class);
        
        PendingIntent pendingIntent = PendingIntent.getActivity(context,MainActivity.REQUEST_CODE,intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                .setContentTitle("Notification for task")
                .setContentText("See your pending task")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.app_logo)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        Log.d("NOTIFICATION CREATED SUCCESS","NOTIFICATION CREATED SUCCESS");
        //set intent
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);

        managerCompat.notify(100, builder.build());

        Log.d("NOTIFIED ","NOTIFIED");
    }
}
