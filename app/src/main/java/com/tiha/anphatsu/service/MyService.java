package com.tiha.anphatsu.service;

import static com.tiha.anphatsu.utils.AppController.Chanel_id;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.tiha.anphatsu.R;
import com.tiha.anphatsu.ui.splash.SplashActivity;

public class MyService extends FirebaseMessagingService {

    public MyService() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Chanel_id);
        RemoteMessage.Notification ii = remoteMessage.getNotification();
        Log.e("NOTIFICATION",new Gson().toJson(ii));
        if (ii==null){
            return;
        }
        builder.setSmallIcon(R.mipmap.ic_launcher_main_round)
                .setContentTitle(remoteMessage.getNotification().getTitle() == null ? "" : remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody() == null ? "" : remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setSound(alarmSound)
                .setContentIntent(pi);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Chanel_id, "FCM_CHANNEL_ID", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("From Token", "Refreshed token: " + token);
    }
}
