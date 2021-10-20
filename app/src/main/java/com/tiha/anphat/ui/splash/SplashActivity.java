package com.tiha.anphat.ui.splash;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.tiha.anphat.main.MainActivity;
import com.tiha.anphat.R;
import com.tiha.anphat.data.AppPreference;
import com.tiha.anphat.data.entities.NewCustomer;
import com.tiha.anphat.ui.login.checkidpass.CheckLoginByIDPassActivity;
import com.tiha.anphat.utils.NetworkUtils;
import com.tiha.anphat.utils.PublicVariables;
import com.tiha.anphat.utils.aes.AESUtils;

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
                        Thread.sleep(2000);
                        String userID = "", passWord = "";
                        AESUtils aesUtils = new AESUtils();
                        try {
                            userID = aesUtils.decrypt(appPreference.getUserID());
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
                        Thread.sleep(2000);
                        Intent intent = new Intent(SplashActivity.this, CheckLoginByIDPassActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception ignored) {
                    }
                }
            });

        }
        thread.start();
    }

    @Override
    public void onLoginSuccess(NewCustomer info) {
        if (info!=null) {
            PublicVariables.UserInfo = info;
            Gson gson = new Gson();
            String json = gson.toJson(info);
            appPreference.setUser(json);
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(SplashActivity.this, CheckLoginByIDPassActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onLoginError(String error) {
        error = error.isEmpty() ? getResources().getString(R.string.error_msg_unknown) : error;
        if (!NetworkUtils.isNetworkConnected(SplashActivity.this)) {
            error = getResources().getString(R.string.error_msg_no_internet);
        }
        if (alertDialog == null || !alertDialog.isShowing()) {
            Bundle bundle = new Bundle();
            bundle.putString("Error", "Đã có lỗi xảy ra. " + error);
            msg = new Message();
            msg.setData(bundle);
            msg.arg1 = 1;
            handler.sendMessage(msg);
        }
        Intent intent = new Intent(SplashActivity.this, CheckLoginByIDPassActivity.class);
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
                                }).setPositiveButton("ĐĂNG NHẬP LẠI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                PublicVariables.ClearData();
                                AppPreference appPreference = new AppPreference(SplashActivity.this);
                                appPreference.setLogin(false);
                                appPreference.setPassWord("");
                                appPreference.setUserID("");

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
