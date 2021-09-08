package com.tiha.anphat.service;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;


public class RestartRealtimeService extends BroadcastReceiver {
    Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
//        context.startService(new Intent(context, RealtimeService.class));
        try {
            this.mContext = context;
            startRealtimeLocationService();
//            context.startService(new Intent(context, CallReceiver.class));
        } catch (Exception e) {
//            AppPreference appPreference = new AppPreference(ctx);
//            appPreference.setError(e.getMessage());
        }
    }

    public void startRealtimeLocationService() {
        RealtimeService service = new RealtimeService();
        Intent mServiceIntent = new Intent(mContext, service.getClass());
        if (!isMyServiceRunning(service.getClass())) {
            ContextCompat.startForegroundService(mContext, mServiceIntent);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
