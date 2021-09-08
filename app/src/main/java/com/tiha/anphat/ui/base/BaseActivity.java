package com.tiha.anphat.ui.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.tiha.anphat.R;
import com.tiha.anphat.utils.CommonUtils;
import com.tiha.anphat.utils.NetworkUtils;


import java.util.Objects;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    Dialog progressDialog;
    String error = "";
    private final int REQUEST_MULTIPLE_PERMISSIONS = 100;
    String[] permissionsMain = {};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!NetworkUtils.isNetworkConnected(this)) {
            error = getResources().getString(R.string.error_msg_no_internet);
            CommonUtils.showMessageError(this, error);
        }
        setContentView(getLayoutResourceId());
        onInit();
        onConfigToolbar();
        onLoadData();

    }

    protected abstract int getLayoutResourceId();

    protected abstract void onLoadData();

    protected abstract void onInit();

    protected abstract void onConfigToolbar();

    protected void showToast(String mToastMsg) {
        Toast.makeText(this, mToastMsg, Toast.LENGTH_LONG).show();
    }

    protected void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (getCurrentFocus() != null)
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Log.e("MultiBackStack", "Failed to add fragment to back stack", e);
        }
    }

    public void checkSelfPermission(String[] permissionsRequired) {
        permissionsMain = permissionsRequired;
        for (String tt:permissionsRequired){
            if (ActivityCompat.checkSelfPermission(this,tt)!= PackageManager.PERMISSION_GRANTED){
                {
                    ActivityCompat.requestPermissions(this, permissionsRequired, REQUEST_MULTIPLE_PERMISSIONS);
                    //return;
                }
            }
        }
    }

    public void showProgressDialog(boolean isShow) {
        if (isShow) {
            progressDialog = new Dialog(this);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(R.color.colorTransparent);
            progressDialog.setCancelable(false);
            progressDialog.setContentView(R.layout.dialog_progressbar_waiting);
            progressDialog.show();
        } else {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
    }

    public void showMessage(String title, String body) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(body)
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.dialog_btn_ok), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_MULTIPLE_PERMISSIONS:
                //Kiem tra tat ca quyen can cap
                boolean allgranted = false;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        allgranted = true;
                    } else {
                        allgranted = false;
                        break;
                    }
                }

                for (String ii : permissionsMain){
                    if (!allgranted && ActivityCompat.shouldShowRequestPermissionRationale(this,ii)){
                        showMessagePermissions();
                    } else {

                    }
                }
                break;
            default:
                break;
        }
    }

    private void showMessagePermissions() {
        new AlertDialog.Builder(this)
                .setTitle("QUYỀN ỨNG DỤNG")
                .setMessage("Ứng dụng cần được cấp nhiều quyền hơn.")
                .setPositiveButton("CẤP QUYỀN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(BaseActivity.this, permissionsMain, REQUEST_MULTIPLE_PERMISSIONS);
                    }
                })
                .setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                })
                .show();
    }

}

