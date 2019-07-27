package com.example.androidnotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button_notify;
    private Button button_cancel;
    private Button button_update;
    //Identifier for notification in a package
    private static final int NOTIFICATION_ID = 0;
    //Identifier for notification channel in a package
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    //Notification Manager
    private NotificationManager mNotifyManager;

    private static final String ACTION_UPDATE_NOTIFICATION =
            "com.example.androidnotification.ACTION_UPDATE_NOTIFICATION";
    private NotificationReciever mReceiver = new NotificationReciever();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Notify Button
        button_notify = findViewById(R.id.notify);
        button_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });
        //Update Button
        button_update = findViewById(R.id.update);
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Update the notification
                updateNotification();
            }
        });
        //Cancel Button
        button_cancel = findViewById(R.id.cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Cancel the notification
                cancelNotification();
            }
        });

        createNotificationChannel();
        //Toggle Button states
        setNotificationButtonState(true, false, false);
        // To receive the ACTION_UPDATE_NOTIFICATION intent, register your broadcast
        // receiver in the onCreate() method
        registerReceiver(mReceiver,new IntentFilter(ACTION_UPDATE_NOTIFICATION));
    }

    //Create and return a Notification builder object
    private NotificationCompat.Builder getNotificationBuilder(){

        Intent intent = new Intent(this,MainActivity.class);
        // By using a PendingIntent to communicate with another app, you are telling that app to execute
        // some predefined code at some point in the future. It's like the other app can perform an
        // action on behalf of your app.
        PendingIntent notificationPendingIntent =  PendingIntent.getActivity(this,
                NOTIFICATION_ID,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent notificationDismissIntent =  PendingIntent.getActivity(this,
                NOTIFICATION_ID,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notifyBuilder =
                new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                        .setContentTitle("You've been notified!")
                        .setContentText("This is your notification text.")
                        .setSmallIcon(R.mipmap.ic_android)
                        // Use the setContentIntent() method from the NotificationCompat.Builder
                        // class to set the content intent
                        .setContentIntent(notificationPendingIntent)
                        // Setting auto-cancel to true closes the notification when user taps on it.
                        .setAutoCancel(true)
                        // Create another pending intent to let the app know that the user has
                        // dismissed the notification, and toggle the button states accordingly.
                        .setDeleteIntent(notificationDismissIntent)
                        // Note: This task is required for devices running Android 7.1 or lower, which
                        // is most Android-powered devices and it's a best practice to
                        // provide backward compatibility support for lower-end devices.
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

        // create an Intent using the custom update action ACTION_UPDATE_NOTIFICATION
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        // Use getBroadcast() to get a PendingIntent. To make sure that this pending intent
        // is sent and used only once, set FLAG_ONE_SHOT
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this,
                NOTIFICATION_ID,updateIntent,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        // Use the addAction() method to add an action to the NotificationCompat.Builder object
        notifyBuilder.addAction(R.drawable.ic_update,"Update Notification",updatePendingIntent);
        mNotifyManager.notify(NOTIFICATION_ID,notifyBuilder.build());
        // Toggle Button state
        setNotificationButtonState(false, true, true);
    }

    public void updateNotification() {

        Bitmap androidImage = BitmapFactory
                .decodeResource(getResources(),R.drawable.mascot_1);
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(androidImage)
                                .setBigContentTitle("Notification Updated!!"));
        mNotifyManager.notify(NOTIFICATION_ID,notifyBuilder.build());
        setNotificationButtonState(false, false, true);

    }

    public void cancelNotification() {
        mNotifyManager.cancel(NOTIFICATION_ID);
        setNotificationButtonState(true, false, false);
    }

    // Toggle buttons state
    void setNotificationButtonState(Boolean isNotifyEnabled, Boolean isUpdateEnabled, Boolean isCancelEnabled) {
        button_notify.setEnabled(isNotifyEnabled);
        button_update.setEnabled(isUpdateEnabled);
        button_cancel.setEnabled(isCancelEnabled);
    }

    public class NotificationReciever extends BroadcastReceiver{

        public NotificationReciever(){

        }
        @Override
        public void onReceive(Context context, Intent intent) {
            // Update the notification
            updateNotification();
        }

    }

    // To unregister your receive
    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
