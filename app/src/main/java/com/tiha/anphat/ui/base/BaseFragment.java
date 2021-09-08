package com.tiha.anphat.ui.base;

import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AlertDialog;

import com.tiha.anphat.R;

public abstract class BaseFragment extends Fragment implements View.OnClickListener{
    Dialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutId(), null);
        onInit(root);
        onLoadData();
        configToolbar();
        return root;
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void onInit(View view);

    protected abstract void onLoadData();

    protected abstract void configToolbar();

    public void showProgressDialog(boolean isShow) {
        if (isShow) {
            progressDialog = new Dialog(getActivity());
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.getWindow().setBackgroundDrawableResource(R.color.colorTransparent);
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //CommonUtils.configShowMessage(builder, title, body);
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
}