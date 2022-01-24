package com.tiha.admin.anphat.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class ScreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                Intent broadcastIntent = new Intent(context, RestartRealtimeService.class);
                context.sendBroadcast(broadcastIntent);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
