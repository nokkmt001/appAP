package com.anphat.supplier.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.anphat.supplier.data.entities.FCMMobileInfo;
import com.anphat.supplier.utils.CommonUtils;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.anphat.supplier.data.entities.CategoryNew;
import com.anphat.supplier.main.MainActivity;
import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.ui.category.CategoryContract;
import com.anphat.supplier.ui.category.CategoryPresenter;
import com.anphat.supplier.ui.login.checkidpass.CheckLoginByIDPassActivity;
import com.anphat.supplier.ui.login.checkphone.CheckPhoneActivity;
import com.anphat.supplier.utils.NetworkUtils;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.aes.AESUtils;

import java.util.List;

public class SplashActivity extends AppCompatActivity implements SplashContract.View, CategoryContract.View, FcmContract.View {
    SplashPresenter presenter;
    AppPreference appPreference;
    AlertDialog alertDialog;
    CategoryPresenter presenterC;
    final String[] token = {""};
    FcmPresenter presenterFCM;
    String tokenMain = "";
    NewCustomer info = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!NetworkUtils.isNetworkConnected(this)) {
            String error = getResources().getString(R.string.error_msg_no_internet);
            CommonUtils.showMessageError(this, error);
            return;
        }
        appPreference = new AppPreference(this);
        presenterC = new CategoryPresenter(this);
        presenterFCM = new FcmPresenter(this);
        try {
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                if (task.isComplete()) {
                    token[0] = task.getResult();
                    tokenMain = token[0];
                    Log.i("AppConstants", "onComplete: new Token got: " + token[0]);
                    PublicVariables.token = token[0];
                }
            });
        } catch (Exception ignored) {
        }

        if (appPreference.getCategory() == null) {
            presenterC.GetCategory();
        }
        setContentView(R.layout.activity_splash);

        presenter = new SplashPresenter(SplashActivity.this, SplashActivity.this);
        presenter.CheckStatusLogin();
    }

    @Override
    public void onCheckStatusLoginSuccess(boolean isLogin) {
        if (isLogin) {
            try {
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

        } else {
            try {
                if (appPreference.isOtp()) {
                    if (appPreference.getUser() != null) {
                        NewCustomer info = new NewCustomer().getNewCustomer(appPreference.getUser());
                        Intent intent = new Intent(SplashActivity.this, CheckLoginByIDPassActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Object", info);
                        intent.putExtras(bundle);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finishAffinity();
                    }

                } else {
                    Intent intent = new Intent(SplashActivity.this, CheckPhoneActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finishAffinity();
                }

            } catch (Exception ignored) {
            }


        }
    }

    @Override
    public void onLoginSuccess(NewCustomer info) {
        if (info != null) {
            this.info = info;
            presenterFCM.getFCM(tokenMain);
            PublicVariables.UserInfo = info;
            Gson gson = new Gson();
            String json = gson.toJson(info);
            appPreference.setUser(json);
            AppPreference.saveUser(info);
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
            } catch (Exception ignored) {

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

    @Override
    public void onInsertFCMSuccess(FCMMobileInfo info) {
        Toast.makeText(getApplicationContext(), "insert fcm success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInsertFCMError(String error) {
        Toast.makeText(getApplicationContext(), "insert fcm error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetFCMSuccess(FCMMobileInfo info) {
        Toast.makeText(getApplicationContext(), "get fcm success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetFCMError(String error) {
        FCMMobileInfo fcmMobileInfo = new FCMMobileInfo();
        fcmMobileInfo.HeDieuHanh = "ANDROID";
        fcmMobileInfo.SoDienThoai = info.getSoDienThoai();
        fcmMobileInfo.Token = tokenMain;
        fcmMobileInfo.NguoiDung = info.getHoTen();
        presenterFCM.insertFCM(fcmMobileInfo);
    }
}
