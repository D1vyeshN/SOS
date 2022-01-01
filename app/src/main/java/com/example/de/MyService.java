package com.example.de;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import static android.content.Intent.ACTION_SCREEN_ON;

public class MyService extends Service {

    @Override

    public int onStartCommand(Intent intent, int flags, int startId){

//        onTaskRemoved(intent);

        IntentFilter filter = new IntentFilter();

        filter.addAction(ACTION_SCREEN_ON);

        BroadcastReceiver Recieve = new MyReceiver(this);

        registerReceiver(Recieve, filter);

        return START_STICKY;

    }

    @Override

    public IBinder onBind(Intent intent) {


        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

//    @Override


    public void onTaskRemoved(Intent rootIntent) {

        Intent restartServiceIntent = new Intent(getApplicationContext(),this.getClass());
       restartServiceIntent.setPackage(getPackageName());
       startService(restartServiceIntent);

       super.onTaskRemoved(rootIntent);

   }

}