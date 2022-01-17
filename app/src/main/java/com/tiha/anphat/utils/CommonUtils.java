package com.tiha.anphat.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;


import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.tiha.anphat.R;

import static android.content.Context.LOCATION_SERVICE;


public class CommonUtils {
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

    public static void showMessageError(Context context, String error) {
        error = error.isEmpty() ? context.getResources().getString(R.string.error_msg_unknown) : error;
        if (!NetworkUtils.isNetworkConnected(context)) {
            error = context.getResources().getString(R.string.error_msg_no_internet);
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.title_error_msg))
                .setMessage(error)
                .setCancelable(false)
                .setPositiveButton(context.getResources().getString(R.string.dialog_btn_ok), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
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
        db.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        if (btnPos != null) db.setPositiveButton(btnPos, ocListener);
        if (btnNeutral != null) db.setNeutralButton(btnNeutral, ocListener);
//        db.setIcon(android.R.drawable.ic_dialog_alert);
        db.show();
    }
    public static BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
