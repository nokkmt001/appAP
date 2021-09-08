package com.tiha.anphat.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.tiha.anphat.MainActivity;
import com.tiha.anphat.R;

import java.util.Timer;
import java.util.TimerTask;

public class RealtimeService extends Service {
    private Timer timer;
    private TimerTask timerTask;
    private static int REQUEST_CHECK_SETTINGS = 789;
    ScreenReceiver screenReceiver;
    NetworkChangeReceiver networkChangeReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground();
        }
        startTimer();
        registerScreenBroadcastReceivers();
        registerNetworkChangeBroadcastReceivers();
        return START_STICKY;
    }

    private void registerScreenBroadcastReceivers() {
        screenReceiver = new ScreenReceiver();
        IntentFilter screenFilter = new IntentFilter();
        screenFilter.addAction(Intent.ACTION_SCREEN_ON);
        screenFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(screenReceiver, screenFilter);
    }

    private void registerNetworkChangeBroadcastReceivers() {
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }


    private void startForeground() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        NotificationCompat.Builder builder;
        Notification notification;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("TiHaGasRunning", "TiHaGas", NotificationManager.IMPORTANCE_MIN);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            notificationChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(getApplicationContext(), notificationChannel.getId());
        } else {
            builder = new NotificationCompat.Builder(getApplicationContext());
        }
        builder = builder
                .setSmallIcon(R.mipmap.ic_launcher_1)
                .setContentTitle("TiHaGas")
                .setContentText("TiHaGas is running...")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        notification = builder.build();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notification.priority = Notification.PRIORITY_MIN;
        }
        startForeground(2019, notification);
    }

    @Override
    public void onDestroy() {
        stoptimertask();
        super.onDestroy();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Intent broadcastIntent = new Intent(this, RestartRealtimeService.class);
            sendBroadcast(broadcastIntent);
            stoptimertask();
        }
        try {
            unregisterReceiver(screenReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        try {
            unregisterReceiver(networkChangeReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // TODO Auto-generated method stub
//        Intent restartService = new Intent(getApplicationContext(), this.getClass());
//        restartService.setPackage(getPackageName());
//        PendingIntent restartServicePI;
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            restartServicePI = PendingIntent.getForegroundService(this, 0, restartService, PendingIntent.FLAG_CANCEL_CURRENT);
//        } else {
//            restartServicePI = PendingIntent.getService(this, 0, restartService, PendingIntent.FLAG_CANCEL_CURRENT);
//        }
//
//
//        //Restart the service once it has been killed android
//        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +100, restartServicePI);
    }

    long period = 60 * 1000;

    public void startTimer() {
        //set a new Timer
        timer = new Timer();
        initializeTimerTask();
        long delay = 0;
        timer.schedule(timerTask, delay, period); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    int i = 0;

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                i++;
            }
        };
    }


}
