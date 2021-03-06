package com.tiha.admin.anphat.ui.splash;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.tiha.admin.anphat.data.entities.UserLoginInfo;
import com.tiha.admin.anphat.main.MainActivity;
import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.data.AppPreference;
import com.tiha.admin.anphat.ui.login.LoginActivity;
import com.tiha.admin.anphat.utils.NetworkUtils;
import com.tiha.admin.anphat.utils.PublicVariables;
import com.tiha.admin.anphat.utils.aes.AESUtils;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {
    Thread thread;
    SplashPresenter presenter;
    AppPreference appPreference;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appPreference = new AppPreference(this);
        presenter = new SplashPresenter(SplashActivity.this, SplashActivity.this);
        presenter.CheckStatusLogin();
    }

    @Override
    public void onCheckStatusLoginSuccess(boolean isLogin) {
        if (isLogin) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        String userID = "", passWord = "";
                        AESUtils aesUtils = new AESUtils();
                        try {
                            userID = aesUtils.decrypt(appPreference.getUserName());
                        } catch (Exception ignored) {
                        }
                        try {
                            passWord = aesUtils.decrypt(appPreference.getPassWord());
                        } catch (Exception ignored) {
                        }
                        presenter.CheckLogin(userID, passWord);
                    } catch (Exception ignored) {
                    }
                }
            });
        } else {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                    Thread.sleep(1000);
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();


                } catch(
                Exception ignored)

                {
                }
            }
        });

    }
        thread.start();
}

    @Override
    public void onLoginSuccess(UserLoginInfo info) {
        if (info != null) {
            PublicVariables.userLoginInfo = info;
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        finish();
    }

    @Override
    public void onLoginError(String error) {
        error = error.isEmpty() ? getResources().getString(R.string.error_msg_unknown) : error;
        if (!NetworkUtils.isNetworkConnected(SplashActivity.this)) {
            error = getResources().getString(R.string.error_msg_no_internet);
        }
        if (alertDialog == null || !alertDialog.isShowing()) {
            Bundle bundle = new Bundle();
            bundle.putString("Error", "???? c?? l???i x???y ra. " + error);
            msg = new Message();
            msg.setData(bundle);
            msg.arg1 = 1;
            handler.sendMessage(msg);
        }
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    Message msg;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            try {
                Bundle bundle = msg.getData();
                String error = bundle.getString("Error");
                if (msg.arg1 == 1) {
                    if (error != null) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                        builder.setTitle(getResources().getString(R.string.title_error_msg))
                                .setMessage(error)
                                .setCancelable(false)
                                .setNegativeButton(getResources().getString(R.string.dialog_btn_ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        alertDialog.dismiss();
                                        dialog.cancel();
                                        moveTaskToBack(true);
                                        finish();
                                    }
                                }).setPositiveButton("????NG NH???P L???I", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                PublicVariables.ClearData();
                                AppPreference appPreference = new AppPreference(SplashActivity.this);
                                appPreference.setLogin(false);
                                appPreference.setPassWord("");
                                appPreference.setUserName("");

                                Intent intent = getBaseContext().getPackageManager().
                                        getLaunchIntentForPackage(getBaseContext().getPackageName());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);
                                SplashActivity.this.finish();
                            }
                        });

                        alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
            } catch (Exception e) {

            }
            return false;
        }
    });

}
