package com.tiha.anphat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tiha.anphat.ui.account.AccountFragment;
import com.tiha.anphat.ui.base.BaseActivity;
import com.tiha.anphat.ui.history.HistoryFragment;
import com.tiha.anphat.ui.home.HomeFragment;
import com.tiha.anphat.ui.pay.PayFragment;
import com.tiha.anphat.ui.sms.SmsFragment;

public class MainActivity extends BaseActivity {
    BottomNavigationView bottomNavigationView;
    FragmentManager fmManager;
    Fragment fmMain, fmHistory, fmPay, fmSms, fmAccount, fmActive;
    String[] permissionsRequired = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onLoadData() {

    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onInit() {
        checkSelfPermission(permissionsRequired);
        fmManager = getFragmentManager();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.navigation_main);
    }

    @Override
    public void onClick(View view) {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_main:
                            if (fmMain == null) {
                                fmMain = new HomeFragment();
                                if (fmActive != null)
                                    fmManager.beginTransaction().add(R.id.frame_container, fmMain, "1").hide(fmActive).commit();
                                else
                                    fmManager.beginTransaction().add(R.id.frame_container, fmMain, "1").commit();
                            } else {
                                fmManager.beginTransaction().hide(fmActive).show(fmMain).commit();
                            }
                            fmActive = fmMain;
                            return true;
                        case R.id.navigation_history:
                            if (fmHistory == null) {
                                fmHistory = new HistoryFragment();
                                if (fmActive != null)
                                    fmManager.beginTransaction().add(R.id.frame_container, fmHistory, "2").hide(fmActive).commit();
                                else
                                    fmManager.beginTransaction().add(R.id.frame_container, fmHistory, "2").commit();
                            } else {
                                fmManager.beginTransaction().hide(fmActive).show(fmHistory).commit();
                            }
                            fmActive = fmHistory;
                            return true;
                        case R.id.navigation_sms:
                            if (fmSms == null) {
                                fmSms = new SmsFragment();
                                if (fmActive != null)
                                    fmManager.beginTransaction().add(R.id.frame_container, fmSms, "4").hide(fmActive).commit();
                                else
                                    fmManager.beginTransaction().add(R.id.frame_container, fmSms, "4").commit();
                            } else {
                                fmManager.beginTransaction().hide(fmActive).show(fmSms).commit();
                            }
                            fmActive = fmSms;
                            return true;
                        case R.id.navigation_pay:
                            if (fmPay == null) {
                                fmPay = new PayFragment();
                                if (fmActive != null)
                                    fmManager.beginTransaction().add(R.id.frame_container, fmPay, "3").hide(fmActive).commit();
                                else
                                    fmManager.beginTransaction().add(R.id.frame_container, fmPay, "3").commit();
                            } else {
                                fmManager.beginTransaction().hide(fmActive).show(fmPay).commit();
                            }
                            fmActive = fmPay;
                            return true;
                        case R.id.navigation_account:
                            if (fmAccount == null) {
                                fmAccount = new AccountFragment();
                                if (fmActive != null)
                                    fmManager.beginTransaction().add(R.id.frame_container, fmAccount, "5").hide(fmActive).commit();
                                else
                                    fmManager.beginTransaction().add(R.id.frame_container, fmAccount, "5").commit();
                            } else {
                                fmManager.beginTransaction().hide(fmActive).show(fmAccount).commit();
                            }
                            fmActive = fmAccount;
                            return true;
                            
                        default:
                            break;
                    }
                    return false;
                }
            };

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("THOÁT ỨNG DỤNG")
                .setMessage("Bạn có chắc muốn thoát ứng dụng?")
                .setCancelable(false)
                .setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        moveTaskToBack(true);
                        finishAffinity();
                    }
                })
                .setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

    }

}