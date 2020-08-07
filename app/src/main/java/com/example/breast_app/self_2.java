package com.example.breast_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class self_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_2);

        createNotificationChannel();
    }

    public void back(View view) {
        onBackPressed();
    }

    public void normal(View view) {
        //pushNotificationAfterTime(12);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ذكرنى لاحقاً")
                .setMessage("قم بتذكيرى بموعد الفحص القادم تلقائياً بعد 10 ثوانى")
                .setPositiveButton("ذكرنى", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(self_2.this,NotificationReciver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(self_2.this,0,intent,0);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                        long timeAtButtomClicked = System.currentTimeMillis();
                        long tenSecondInMillis = 1000 * 10;
                        alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtomClicked + tenSecondInMillis, pendingIntent);

                    }
                })
                .setNegativeButton("تخطى", null);
        builder.setCancelable(false);
        builder.create().show();

    }

    private void pushNotificationAfterTime(long time) {
        //
        //time = 1000 * 60 * 60 * 24; // for one day.
        //
        Intent notifyIntent = new Intent(this,NotificationReciver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
                1000 * 60 * 60 * 24, pendingIntent);

        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    time, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        }

        alarmManager.set(AlarmManager.RTC_WAKEUP,time, pendingIntent);//set alarm manager with entered timer by converting into milliseconds

    }

    public void createNotificationChannel() {
        CharSequence name = "wa3iaReminderChannel";
        String description = "Channel for wa3ia remainder";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("notifyWa3ia",name,importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }


}
