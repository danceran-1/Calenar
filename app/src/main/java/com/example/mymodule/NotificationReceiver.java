package com.example.mymodule;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String reminderText = intent.getStringExtra("reminderText");
        String title = intent.getStringExtra("title");

        // Проверяем, есть ли разрешение на уведомления
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "EVENT_REMINDER")
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle(title)  // Заголовок уведомления
                    .setContentText(reminderText) // Текст уведомления
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify((int) System.currentTimeMillis(), builder.build());
        }
    }
}

