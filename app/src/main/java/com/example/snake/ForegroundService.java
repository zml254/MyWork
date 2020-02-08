package com.example.snake;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class ForegroundService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification = getNotification();
        startForeground(1,notification);
        return START_STICKY;
    }

    public Notification getNotification() {
        Intent intent1 = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,
                0, intent1, 0);
        NotificationCompat.Builder nb = new NotificationCompat.Builder(this, "1");
        nb.setContentIntent(pi)
                .setContentTitle("Snake")
                .setContentText("游戏正在运行中")
                .setWhen(System.currentTimeMillis())
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1", "name",
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            nb.setChannelId("1");
        }
        return nb.build();
    }

}
