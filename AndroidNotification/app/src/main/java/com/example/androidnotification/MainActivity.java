package com.example.androidnotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button_notify;
    //Identifier for notification in a package
    private static final int NOTIFICATION_ID = 0;
    //Identifier for notification channel in a package
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    //Notification Manager
    private NotificationManager mNotifyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_notify = findViewById(R.id.notify);
        button_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });
        createNotificationChannel();
    }

    //Create and return a Notification builder object
    private NotificationCompat.Builder getNotificationBuilder(){

        Intent intent = new Intent(this,MainActivity.class);
        // By using a PendingIntent to communicate with another app, you are telling that app to execute
        // some predefined code at some point in the future. It's like the other app can perform an
        // action on behalf of your app.
        PendingIntent notificationPendingIntent =  PendingIntent.getActivity(this,
                NOTIFICATION_ID,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notifyBuilder =
                new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                        .setContentTitle("You've been notified!")
                        .setContentText("This is your notification text.")
                        .setSmallIcon(R.mipmap.ic_android)
                        // Use the setContentIntent() method from the NotificationCompat.Builder class to set the content intent
                        .setContentIntent(notificationPendingIntent)
                        // Setting auto-cancel to true closes the notification when user taps on it.
                        .setAutoCancel(true)
                        // Note: This task is required for devices running Android 7.1 or lower, which
                        // is most Android-powered devices and it's a best practice to provide backward compatibility and
                        // support for lower-end devices.
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        // Set the sound, vibration, and LED-color pattern for your notification
                        // if the user's device has an LED indicator to the default values.
                        .setDefaults(NotificationCompat.DEFAULT_ALL);


        return notifyBuilder;
    }

    public void createNotificationChannel(){
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            // Create a NotificationChannel
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Mascot Notification", NotificationManager.IMPORTANCE_HIGH);

            // Initial settings for notification channel
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    //Send the notification builded previously
    public void sendNotification(){
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID,notifyBuilder.build());
    }
}
