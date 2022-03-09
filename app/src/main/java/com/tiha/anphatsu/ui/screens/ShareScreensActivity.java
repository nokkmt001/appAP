package com.tiha.anphatsu.ui.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.wallet.firstparty.GetBuyFlowInitializationTokenRequest;
import com.sendbird.calls.SendBirdCall;
import com.sendbird.calls.SendBirdException;
import com.sendbird.calls.SendBirdVideoView;
import com.tiha.anphatsu.R;

public class ShareScreensActivity extends AppCompatActivity {
    private static final int SCREEN_CAPTURE_PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = "GGGGGGGG";
    Button butShareScreen;
    SendBirdCall mCurentCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_screens);
        butShareScreen = findViewById(R.id.butShareScreen);
        butShareScreen.setOnClickListener(v -> startScreenShare());
    }

    @TargetApi(21)
    private void startScreenShare() {
        MediaProjectionManager mediaProjectionManager =
                (MediaProjectionManager) getApplication().getSystemService(
                        Context.MEDIA_PROJECTION_SERVICE);
        if (mediaProjectionManager != null) {
            startActivityForResult(
                    mediaProjectionManager.createScreenCaptureIntent(),
                    SCREEN_CAPTURE_PERMISSION_REQUEST_CODE
            );
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == SCREEN_CAPTURE_PERMISSION_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.i(TAG, "Screen capture permission request done");
                shareMyScreenAfterAcceptingPermission(intent);
            }
        }
    }
    private void shareMyScreenAfterAcceptingPermission(Intent screenCaptureIntent) {
        if (mCurentCall == null) {
            return;
        }
//        SendBirdCall.startScreenShare(screenCaptureIntent, new CompletionHandler() {
//            @Override
//            public void onResult(@Nullable SendBirdException e) {
//                if (e != null) {
//                    e.printStackTrace();
//                    Toast.makeText(
//                            mContext,
//                            "Error starting screen share",
//                            Toast.LENGTH_LONG
//                    ).show();
//                } else {
//                    Toast.makeText(
//                            mContext,
//                            "Screen sharing in progress",
//                            Toast.LENGTH_LONG
//                    ).show();
//                    // Show stop screen share button
//                    butStopShareScreen.setVisibility(View.VISIBLE);
//                    // Hide the share screen button
//                    butShareScreen.setVisibility(View.GONE);
//                }
//            }
//        });
    }
    private void stopScreenShare() {
        if (mCurentCall == null) {
            return;
        }
//        mCurentCall.stopScreenShare(new CompletionHandler() {
//            @Override
//            public void onResult(@Nullable SendBirdException e) {
//                // Hide stop screen share button
//                butStopShareScreen.setVisibility(View.GONE);
//                // Show Share screen button
//                butShareScreen.setVisibility(View.VISIBLE);
//            }
//        });
    }


}