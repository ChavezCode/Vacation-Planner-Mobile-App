package com.example.vacationplanner.UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.vacationplanner.R;

public class MyReceiver extends BroadcastReceiver {
    //you need a channel for API 26 to set notifications
    String channelStart_id = "start";
    String channelEnd_id= "end";
    //2 notification ID's to distinguish between both notifications.
    static int notificationID = 0;


    @Override
    public void onReceive(Context context, Intent intent) {


        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast. Start
        String startMessage = intent.getStringExtra("start");
        String endMessage = intent.getStringExtra("end");
        if (startMessage != null) {
            Toast.makeText(context, intent.getStringExtra("start"), Toast.LENGTH_LONG).show();
            createStartNotificationChannel(context, channelStart_id);
            Notification nStart = new NotificationCompat.Builder(context, channelStart_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(intent.getStringExtra("start"))
                    .setContentTitle("NotificationStart").build();
            NotificationManager notificationManagerStart = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManagerStart.notify(notificationID++, nStart);
        }
        if (endMessage != null) {
            Toast.makeText(context, intent.getStringExtra("end"), Toast.LENGTH_LONG).show();
            createEndNotificationChannel(context, channelEnd_id);
            Notification nEnd = new NotificationCompat.Builder(context, channelEnd_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(intent.getStringExtra("end"))
                    .setContentTitle("NotificationEnd").build();
            NotificationManager notificationManagerEnd = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManagerEnd.notify(notificationID++, nEnd);
        }
    }
    //you need a channel for API 26 to set notifications
    private void createStartNotificationChannel(Context context, String CHANNEL_ID) {
        CharSequence name = "mystartchannelname";
        String description = "mystartchannelname";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        //needs the following to go off
        channel.setDescription(description);
        NotificationManager notificationManager=context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void createEndNotificationChannel(Context context, String CHANNEL_ID) {
        CharSequence name = "myendchannelname";
        String description = "myendchannelname";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        //needs the following to go off
        channel.setDescription(description);
        NotificationManager notificationManager=context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}