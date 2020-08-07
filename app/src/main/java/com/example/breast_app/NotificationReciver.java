package com.example.breast_app;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReciver extends BroadcastReceiver {
    public NotificationReciver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent2 = new Intent(context, self.class);
        intent2.setAction(Long.toString(System.currentTimeMillis()));
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent2, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyWa3ia")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("عليك القيام بالفحص الذاتى")
                .setContentText("قم بفحص سرطان الثدى من خلال فيديو التعليمات فى تطبيق واعية")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200,builder.build());
        /*
        Intent intent1 = new Intent(context, NotificationIntentService.class);
        context.startService(intent1);

        NotificationReciver.completeWakefulIntent(intent);

        */
    }
}
