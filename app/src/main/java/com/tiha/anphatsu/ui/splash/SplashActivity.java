package com.tiha.anphatsu.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.tiha.anphatsu.data.entities.CategoryNew;
import com.tiha.anphatsu.main.MainActivity;
import com.tiha.anphatsu.R;
import com.tiha.anphatsu.data.AppPreference;
import com.tiha.anphatsu.data.entities.NewCustomer;
import com.tiha.anphatsu.ui.category.CategoryContract;
import com.tiha.anphatsu.ui.category.CategoryPresenter;
import com.tiha.anphatsu.ui.login.checkidpass.CheckLoginByIDPassActivity;
import com.tiha.anphatsu.ui.login.checkphone.CheckPhoneActivity;
import com.tiha.anphatsu.utils.NetworkUtils;
import com.tiha.anphatsu.utils.PublicVariables;
import com.tiha.anphatsu.utils.aes.AESUtils;

import java.util.List;

public class SplashActivity extends AppCompatActivity implements SplashContract.View, CategoryContract.View {
    Thread thread;
    SplashPresenter presenter;
    AppPreference appPreference;
    AlertDialog alertDialog;
    CategoryPresenter presenterC;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPreference = new AppPreference(this);
        presenterC = new CategoryPresenter(this);
        if (appPreference.getCategory()==null) {
            presenterC.GetCategory();
        }
        setContentView(R.layout.activity_splash);

        presenter = new SplashPresenter(SplashActivity.this, SplashActivity.this);
        presenter.CheckStatusLogin();
    }

    @Override
    public void onCheckStatusLoginSuccess(boolean isLogin) {
        if (isLogin) {
            thread = new Thread(() -> {
                try {
                    Thread.sleep(1000);
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
            });
        } else {
            thread = new Thread(() -> {
                try {
                    if (appPreference.isOtp()){
                        Thread.sleep(1000);
                        if (appPreference.getUser()!=null){
                            NewCustomer info = new NewCustomer().getNewCustomer(appPreference.getUser());
                            Intent intent = new Intent(SplashActivity.this, CheckLoginByIDPassActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Object", info);
                            intent.putExtras(bundle);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }

                    } else {
                        Thread.sleep(1000);
                        Intent intent = new Intent(SplashActivity.this, CheckPhoneActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception ignored) {
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
            Intent intent = new Intent(SplashActivity.this, CheckPhoneActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
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
                                .setNegativeButton(getResources().getString(R.string.dialog_btn_ok), (dialog, which) -> {
                                    alertDialog.dismiss();
                                    dialog.cancel();
                                    moveTaskToBack(true);
                                    finish();
                                }).setPositiveButton("ĐĂNG NHẬP LẠI", (dialogInterface, i) -> {
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

    @Override
    public void onGetCategorySuccess(List<CategoryNew> list) {
        appPreference.saveCategory(list);
    }

    @Override
    public void onGetCategoryError(String error) {
        appPreference.clearCategory();
    }
}
