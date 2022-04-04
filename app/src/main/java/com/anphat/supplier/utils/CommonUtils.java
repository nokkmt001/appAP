package com.anphat.supplier.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.media.Image;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;


import com.anphat.supplier.R;
import com.anphat.supplier.ui.base.BaseEventClick;
import com.anphat.supplier.ui.cart.CartActivity;

import static android.content.Context.LOCATION_SERVICE;


public class CommonUtils {

    public interface OnTextChange {
        void onChange(String text);
    }

    public static BaseEventClick.OnClickListener onClickListener;

    public void setOnClickListener(BaseEventClick.OnClickListener onClickListener) {
        CommonUtils.onClickListener = onClickListener;
    }

    public static void configToolbar(Context mContext, ActionBar actionBar, String tieuDeForm, int indexImage) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(tieuDeForm);
        switch (indexImage) {
            case 0:
                actionBar.setHomeAsUpIndicator(mContext.getResources().getDrawable(R.drawable.ic_arrow_back));
                break;
            case 1:
                actionBar.setHomeAsUpIndicator(mContext.getResources().getDrawable(R.drawable.ic_close_form));
                break;
            default:
                break;
        }
    }

    public static void ActionbarSearch(Context context,EditText et, ImageView imageBack, ImageView imageCart, ImageView imageNotification,OnTextChange onTextChange) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                new Handler().postDelayed(() -> onTextChange.onChange(s.toString()),1000);
            }
        });

        imageBack.setOnClickListener(v -> onClickListener.onClick(v,0));

        imageCart.setOnClickListener(v -> {
            Intent intent = new Intent(context, CartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        });

        imageNotification.setOnClickListener(v -> {

        });
    }

    public static void showMessageError(Context context, String error) {
        error = error.isEmpty() ? context.getResources().getString(R.string.error_msg_unknown) : error;
        if (!NetworkUtils.isNetworkConnected(context)) {
            error = context.getResources().getString(R.string.error_msg_no_internet);
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.title_error_msg))
                .setMessage(error)
                .setCancelable(false)
                .setPositiveButton(context.getResources().getString(R.string.dialog_btn_ok), (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public static boolean checkLocation(Context context) {
        final LocationManager manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    static public void alertDialog(Context context, String title, String message, String btnPos, String btnNeutral, DialogInterface.OnClickListener ocListener) {
        AlertDialog.Builder db = new AlertDialog.Builder(context);
        db.setTitle(title);
        db.setMessage(message);
        db.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());
        if (btnPos != null) db.setPositiveButton(btnPos, ocListener);
        if (btnNeutral != null) db.setNeutralButton(btnNeutral, ocListener);
//        db.setIcon(android.R.drawable.ic_dialog_alert);
        db.show();
    }
}
