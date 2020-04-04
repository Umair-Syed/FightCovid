package com.skapps.android.fightcovid;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import static android.graphics.Color.RED;
import static androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC;

/**
 * Created by Syed Umair on 03/04/2020.
 */
public class NotificationUtils {

    private static final int UPDATE_USER_PENDING_INTENT_ID = 5353;
    private static final String UPDATE_USER_NOTIFICATION_CHANNEL_ID = "mainChannelID";


    public static void notifyUserOfUpdate(Context context, String title, String body, int id){

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    UPDATE_USER_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            mChannel.enableLights(true);
            mChannel.setLightColor(RED);
            mChannel.setLockscreenVisibility(VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(mChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, UPDATE_USER_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);


        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }


        notificationManager.notify(id, notificationBuilder.build());
    }


    private static PendingIntent contentIntent(Context context){
        Intent startActivityIntent = new Intent(context, MainActivity.class);

        return PendingIntent.getActivity(
                context,
                UPDATE_USER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }


}
