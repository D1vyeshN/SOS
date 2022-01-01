package com.example.de;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import static android.content.Intent.ACTION_SCREEN_ON;

public class MyService2 extends Service {
    public MyService2() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();

        filter.addAction(ACTION_SCREEN_ON);

        BroadcastReceiver Recieve = new MyReceiver(this);

        registerReceiver(Recieve, filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}