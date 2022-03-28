package com.anphat.supplier.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;

public class DownLoadService extends IntentService {

   public static volatile boolean shouldStop = false;

   public static final String ACTION_1 ="MY_ACTION_1";

   public static final String PARAM_PERCENT = "percent";

   public DownLoadService() {
      super("SimpleIntentService");
   }

   @Override
   protected void onHandleIntent(Intent intent) {

      // Create Intent object (to broadcast).
      Intent broadcastIntent = new Intent();

      // Set Action name for this Intent.
      // A Intent can perform many different actions.
      broadcastIntent.setAction(DownLoadService.ACTION_1);

      // Loop 100 times broadcast of Intent.
      for (int i = 0; i <= 100; i++) {

         // Set data
         // (Percent of work)
         broadcastIntent.putExtra(PARAM_PERCENT, i);

         // Send broadcast
         sendBroadcast(broadcastIntent);

         // Sleep 100 Milliseconds.
         SystemClock.sleep(100);

         if(shouldStop) {
            stopSelf();
            return;
         }
      }

   }
}