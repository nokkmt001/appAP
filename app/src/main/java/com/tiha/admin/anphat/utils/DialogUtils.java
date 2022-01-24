package com.tiha.admin.anphat.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.arch.core.util.Function;

public class DialogUtils {
    public final void showDialog(Context context, String title, String message,String textOK,boolean cancelable, final Function okFunc){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(cancelable)
                .setPositiveButton(textOK, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
//                        okFunc();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("HỦY BỎ", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
