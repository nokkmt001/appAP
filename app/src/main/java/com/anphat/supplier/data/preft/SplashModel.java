package com.anphat.supplier.data.preft;

import android.content.Context;

import com.anphat.supplier.data.AppPreference;

public class SplashModel {
    Context context;

    public SplashModel(Context context) {
        this.context = context;
    }

    public boolean CheckStatusLogin() {
        AppPreference appPreference = new AppPreference(context);
        return appPreference.isLogin();
    }
}
