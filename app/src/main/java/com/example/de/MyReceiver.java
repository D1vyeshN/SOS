package com.example.de;

import android.annotation.SuppressLint;
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

public class MyReceiver extends BroadcastReceiver {

    private int Count = 0;

    private long PreviousTime;

    private long CurrentTime;

    private int NOTIFICATION_ID = 1;

    private final String KEY_TEXT_REPLY = "key_text_reply";

    NotificationCompat.Builder mBuilder;

    NotificationManager mNotificationManager;

    NotificationManagerCompat mNotificationManagerForLess;

    private Context context;

 //   String myMessage = "I'm in TROUBLE";

    public MyReceiver(Context myContext) {

        this.context = myContext;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ShowToast")

    @Override

    public void onReceive(Context context, Intent intent) {

        if(Count == 0){

            PreviousTime = System.currentTimeMillis();

        }

        Count += 1;


        if(Count == 2){

            CurrentTime = System.currentTimeMillis();

            if(CurrentTime < (PreviousTime + 5000)){

                new SendSMS(context).SendMessage();

//                String replyLabel = "Add Message";
//                RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
//                        .setLabel(replyLabel)
//                        .build();
//
//                Intent intentActionYes = new Intent(context, ActionReceiver.class);
//
//                Intent intentActionNo = new Intent(context, ActionReceiver.class);
//
//                Intent intentActionREPLY = new Intent(context, ActionReceiver.class);
//
//                intentActionYes.putExtra("action","actionNameOne");
//
//                intentActionNo.putExtra("action","actionNameTwo");
//
//                intentActionREPLY.putExtra("action","actionNameThree");
//
//
//                PendingIntent Yes = PendingIntent.getBroadcast(context,1,intentActionYes,PendingIntent.FLAG_UPDATE_CURRENT);
//
//                PendingIntent No = PendingIntent.getBroadcast(context,2,intentActionNo,PendingIntent.FLAG_UPDATE_CURRENT);
//
//                PendingIntent REPLY = PendingIntent.getBroadcast(context,3,intentActionREPLY,PendingIntent.FLAG_UPDATE_CURRENT);
//
//                NotificationCompat.Action action =
//                        new NotificationCompat.Action.Builder(R.drawable.ic_baseline_message_24,
//                                "REPLY", REPLY)
//                                .addRemoteInput(remoteInput)
//                                .build();
//
//
//                Bitmap icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.de_logo_demo);
//
//                mBuilder = new NotificationCompat.Builder(context, "One")
//                        .setSmallIcon(R.drawable.de_logo_demo)
//                        .setColor(ContextCompat.getColor(context, R.color.teal_700))
//                        .setLargeIcon(icon)
//                        .setContentTitle("Safety")
//                        .setContentText("Want to send Message?")
//                        .setPriority(Notification.PRIORITY_MAX)
//                        .addAction(R.drawable.ic_baseline_message_24, "Yes", Yes)
//                        .addAction(R.drawable.ic_baseline_message_24,"No",No)
//                        .addAction(action)
//                        .setAutoCancel(true);
//                        .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText("Much longer text that cannot fit one line..."));





//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//                {
//
//                    mNotificationManager =
//                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//                    Toast.makeText(context, "inside it", Toast.LENGTH_LONG).show();
//                    String channelId = "My_channel_id";
//                    NotificationChannel channel = new NotificationChannel(
//                            channelId,
//                            "First Channel",
//                            NotificationManager.IMPORTANCE_HIGH);
//                    mNotificationManager.createNotificationChannel(channel);
//                    mBuilder.setChannelId(channelId);
//
//                    mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
//                }
//                else{
//
//                    mNotificationManagerForLess =  NotificationManagerCompat.from(context);
//
//                    mNotificationManagerForLess.notify(NOTIFICATION_ID, mBuilder.build());
//
//                }



                Count = 0;

            }
            else{

                Count = 0;

            }

        }

    }

}

