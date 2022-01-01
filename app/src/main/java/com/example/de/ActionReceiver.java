package com.example.de;

import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

public class ActionReceiver extends BroadcastReceiver {

    private static final String KEY_TEXT_REPLY = "key_text_reply";

    NotificationManagerCompat mNotificationManager;

    String myMessage = "I'm in TROUBLE";

    private int NOTIFICATION_ID = 1;

//    private Context context;

//    public ActionReceiver(Context myContext) {
//
//        this.context = myContext;
//
//    }

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override

    public void onReceive(Context context, Intent intent) {

        mNotificationManager =  NotificationManagerCompat.from(context);

        String action=intent.getStringExtra("action");

        if(action.equals("actionNameOne")){

        //    new SendSMS(context).SendMessage(myMessage);

            mNotificationManager.cancel(NOTIFICATION_ID);

        }
        else if(action.equals("actionNameTwo")){

            mNotificationManager.cancel(NOTIFICATION_ID);

//            Toast.makeText(context,action,Toast.LENGTH_SHORT).show();

        }
        else if(action.equals("actionNameThree")){

            Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);

            if (remoteInput != null) {

                if(String.valueOf(remoteInput.getCharSequence(KEY_TEXT_REPLY)).equals("")){

                    Toast.makeText(context,String.valueOf(remoteInput.getCharSequence(KEY_TEXT_REPLY)),Toast.LENGTH_LONG).show();

        //            new SendSMS(context).SendMessage(myMessage);

                }
                else{

       //             new SendSMS(context).SendMessage(String.valueOf(remoteInput.getCharSequence(KEY_TEXT_REPLY)));

                }

            }

            mNotificationManager.cancel(NOTIFICATION_ID);

        }

        //This is used to close the notification tray

        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

        context.sendBroadcast(it);
    }

}

