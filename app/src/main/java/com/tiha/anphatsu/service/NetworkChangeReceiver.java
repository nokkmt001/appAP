package com.tiha.anphatsu.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
//            if (isOnline(context)) {
//
//            } else {
//                Log.e("NetworkChangeReceiver", "Conectivity Failure !!! ");
//            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                Intent broadcastIntent = new Intent(context, RestartRealtimeService.class);
                context.sendBroadcast(broadcastIntent);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
