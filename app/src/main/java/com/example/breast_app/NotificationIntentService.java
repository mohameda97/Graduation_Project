package com.example.breast_app;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

public class NotificationIntentService extends IntentService {
    private static final int NOTIFICATION_ID = 3;

    public NotificationIntentService() {
        super("MyNewIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        //show notfication
        sendNotification("عليك القيام بالفحص الذاتى","قم بفحص سرطان الثدى من خلال فيديو التعليمات فى تطبيق واعية");
    }


    /**
     * Create and show a simple notification containing the received FCM message.
     */
    private void sendNotification(String title,
                                  String message) {


        if (title == null)
            title = getApplicationContext().getResources().getString(R.string.app_name);
        Intent intent= new Intent(getApplicationContext(),self.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        int color = getResources().getColor(R.color.colorPrimary);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.MessagingStyle messagingStyle =
                new NotificationCompat.MessagingStyle(title);
        messagingStyle.addMessage(new NotificationCompat.MessagingStyle.Message(message,
                System.currentTimeMillis(), title));

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel("1", "General", NotificationManager.IMPORTANCE_HIGH);

            Notification notification = new Notification.Builder(getApplicationContext(),mChannel.getId())
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setChannelId("1")
                    .setContentIntent(pendingIntent)
                    .setColor(color)
                    .setAutoCancel(true)
                    .build();

            mChannel.setSound(defaultSoundUri,null);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);

            mNotificationManager.createNotificationChannel(mChannel);

            mNotificationManager.notify(100, notification);
        }else {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"1")
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setColor(color)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            mNotificationManager.notify(100, builder.build());
        }

    }
}