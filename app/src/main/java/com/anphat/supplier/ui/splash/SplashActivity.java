package com.anphat.supplier.ui.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.anphat.supplier.data.entities.FCMMobileInfo;
import com.anphat.supplier.data.entities.condition.CustomerLoginCondition;
import com.anphat.supplier.databinding.ActivitySplashBinding;
import com.anphat.supplier.ui.base.BaseMVVMActivity;
import com.anphat.supplier.utils.AppUtils;
import com.anphat.supplier.viewmodel.LoginViewModel;
import com.anphat.supplier.utils.CommonUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.anphat.supplier.ui.main.MainActivity;
import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.ui.login.checkidpass.CheckLoginByIDPassActivity;
import com.anphat.supplier.ui.login.checkphone.CheckPhoneActivity;
import com.anphat.supplier.utils.NetworkUtils;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.aes.AESUtils;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseMVVMActivity<ActivitySplashBinding, LoginViewModel> {
    AppPreference appPreference;
    final String[] token = {""};
    String tokenMain = "";
    NewCustomer info = null;
    AppUpdateManager appUpdateManager;

    @Override
    protected void initView() {
        super.initView();
        if (!NetworkUtils.isNetworkConnected(this)) {
            String error = getResources().getString(R.string.error_msg_no_internet);
            CommonUtils.showMessageError(this, error);
        }
        viewModel.checkUpdate();
    }

    public void checkLogin() {
        appPreference = new AppPreference(this);
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

        if (appPreference.isLogin()) {
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
                CustomerLoginCondition condition = new CustomerLoginCondition();
                condition.NguoiDungMobielID = userID;
                condition.Password = passWord;
                viewModel.GetLoginByIDPassWord(condition);
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
    protected Class<LoginViewModel> getClassVM() {
        return LoginViewModel.class;
    }

    @Override
    public ActivitySplashBinding getViewBinding() {
        return ActivitySplashBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.mItemCheckIDPass.observe(this, info -> {
            if (info != null) {
                Log.i("TOKEN--------------", PublicVariables.token);
                this.info = info;
                insertFCM();
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
        });

        viewModel.mItemInsertFCM.observe(this, fcmMobileInfo -> {
            showToast("insert fcm success");
        });

        viewModel.itemMain.observe(this, result -> {
            if (result == null) {
                checkLogin();
                return;
            }
            String version = "";
            try {
                PackageInfo pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
                version = pInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (!result.getLatestVersion().equals(version)) {
                isShowUpdate();
            } else {
                checkLogin();
            }
        });
    }

    public void insertFCM(){
        FCMMobileInfo fcmMobileInfo = new FCMMobileInfo();
        fcmMobileInfo.HeDieuHanh = "ANDROID";
        fcmMobileInfo.SoDienThoai = info.getSoDienThoai();
        fcmMobileInfo.Token = PublicVariables.token;
        fcmMobileInfo.NguoiDung = info.getHoTen();
        viewModel.insertFCM(fcmMobileInfo);
    }

    @Override
    public void onClick(View view) {

    }

    public int MY_REQUEST_CODE = 1;

    private void popupSnackbarForCompleteUpdate(View view1) {
        Snackbar snackbar =
                Snackbar.make(
                        view1,
                        "An update has just been downloaded.",
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("RESTART", view -> appUpdateManager.completeUpdate());
        snackbar.setActionTextColor(
                getResources().getColor(R.color.Blue_App));
        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                System.out.print("Update flow failed! Result code: " + resultCode);
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }
    }

    public void showUpdate() {
        @SuppressLint("ShowToast") Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "New app is ready", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", view -> {
            AppUtils.openPlayStoreForApp(this);
            snackbar.dismiss();
        });
        snackbar.setAction("Cancel", view -> {
            checkLogin();
            snackbar.dismiss();
        });
        snackbar.show();
    }

    public void isShowUpdate() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cập nhật")
                .setMessage("Vui lòng cập nhật phần mềm để có trải nghiệm tốt nhất")
                .setCancelable(false)
                .setPositiveButton("CÓ", (dialog, id) -> {
                    dialog.cancel();
                    AppUtils.openPlayStoreForApp(this);
                })
                .setNegativeButton("KHÔNG", (dialog, id) -> {
                    dialog.cancel();
                    moveTaskToBack(true);
                    finishAffinity();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}
