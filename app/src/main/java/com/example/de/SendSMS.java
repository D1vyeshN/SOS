package com.example.de;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.M)

public class SendSMS {

      private int ID = -1;

      private String meName;

    private int NOTIFICATION_ID = 1;

      private FusedLocationProviderClient fusedLocationProviderClient;

      LogInDatabaseHandler DB;

      DatabaseHandler db;

      String SMS;

    NotificationCompat.Builder mBuilder;

    NotificationManager mNotificationManager;

    NotificationManagerCompat mNotificationManagerForLess;


    private Context context;

     public SendSMS(Context myContext) {

         this.context = myContext;

     }

     public void SendMessage() {

             DB = new LogInDatabaseHandler(context);

             List<User> users = DB.getAllUsers();

             for(User us : users){

                     if(us.get_logStatus().equals("true")){

                             ID = us.getID();

                             meName = us.getName();

                             break;

                     }

             }

             SMS = ("I'm  " + meName + "  in TROUBLE");

             if(ID != -1) {

                 fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

                     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                             if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {

                                     if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                             Toast.makeText(context, "Please Provide all Permission", Toast.LENGTH_SHORT).show();

                                             return;
                                     }
                                     fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {

                                             if (location != null) {

                                                     Double Latitude = location.getLatitude();

                                                     Double Longitude = location.getLongitude();

                                                     String Loc = "https://www.google.com/maps/dir/?api=1&destination=" + Latitude + "," + Longitude;

                                                     try {

                                                            int cout = 0;

                                                             db = new DatabaseHandler(context);

                                                             List<Contact> contacts = db.getAllContactsKey(ID);

                                                             SmsManager smsManager = SmsManager.getDefault();

                                                             for (Contact cn : contacts) {

                                                                     if(cn.get_status().equals("true")) {

                                                                         smsManager.sendTextMessage(cn.getPhoneNumber(), null, (SMS + "\n" + Loc), null, null);



//
//                                                                             Toast.makeText(context, cn.getName(), Toast.LENGTH_SHORT).show();

//                                                                         Toast.makeText(context, cn.getPhoneNumber(), Toast.LENGTH_LONG).show();

                                                                             cout += 1;
                                                                     }

                                                             }

                                                         Bitmap icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.de_logo_demo);

                                                         mBuilder = new NotificationCompat.Builder(context, "One")
                                                                 .setSmallIcon(R.drawable.de_logo_demo)
                                                                 .setColor(ContextCompat.getColor(context, R.color.teal_700))
                                                                 .setLargeIcon(icon)
                                                                 .setContentTitle("Safety")
                                                                 .setContentText("Message sended")
                                                                 .setPriority(Notification.PRIORITY_MAX)
                                                                 .setTimeoutAfter(5000)
                                                                 .setAutoCancel(true);

//                                                             Toast.makeText(context, String.valueOf(cout), Toast.LENGTH_SHORT);

                                                         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                                                         {

                                                             mNotificationManager =
                                                                     (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

//                                                             Toast.makeText(context, "inside it", Toast.LENGTH_LONG).show();

                                                             String channelId = "My_channel_id";
                                                             NotificationChannel channel = new NotificationChannel(
                                                                     channelId,
                                                                     "First Channel",
                                                                     NotificationManager.IMPORTANCE_HIGH);
                                                             mNotificationManager.createNotificationChannel(channel);
                                                             mBuilder.setChannelId(channelId);

                                                             mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
                                                         }
                                                         else{

                                                             mNotificationManagerForLess =  NotificationManagerCompat.from(context);

                                                             mNotificationManagerForLess.notify(NOTIFICATION_ID, mBuilder.build());

                                                             AutoDismissNotification.setAlarm(context, 2, 5000L);


                                                         }

                                                     } catch (Exception e) {

                                                             e.printStackTrace();

                                                             Toast.makeText(context, "Failed to send message", Toast.LENGTH_SHORT).show();

                                                         Log.d("er", "Failed to send message");

                                                         Log.d("er", String.valueOf(e));
                                                     }

                                             }

                                     });

                             } else {

                                     Toast.makeText(context, "Please Provide all Permission", Toast.LENGTH_SHORT).show();
                             }
                     }
             }

     }

}
