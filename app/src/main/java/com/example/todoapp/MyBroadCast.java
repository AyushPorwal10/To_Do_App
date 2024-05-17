package com.example.todoapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
            Log.d("GOING TO CREATE NOTIFICATION ","GOING TO CREATE NOTIFICATION ");
            Notification_Helper.createNotification(context);
    }
}
