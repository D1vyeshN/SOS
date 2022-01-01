package com.example.de;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;
import androidx.core.content.ContextCompat;

public class AutoDismissNotification extends BroadcastReceiver {

    private static final String KEY_EXTRA_NOTIFICATION_ID = "key_text_reply";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ShowToast")

    @Override

    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        notificationManager.cancel(intent.getIntExtra(KEY_EXTRA_NOTIFICATION_ID, 0));

    }

    public static void setAlarm(Context context,  int notificationId, Long time) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AutoDismissNotification.class);
        alarmIntent.putExtra(KEY_EXTRA_NOTIFICATION_ID, notificationId);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, notificationId, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, alarmPendingIntent);
    }

    public static void cancelAlarm(Context context, int notificationId) {
        Intent alarmIntent = new Intent(context, AutoDismissNotification.class);
        alarmIntent.putExtra(KEY_EXTRA_NOTIFICATION_ID, notificationId);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, notificationId, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(alarmPendingIntent);
    }

}


