package com.tiha.anphat.data.preft;

import android.content.Context;

import com.tiha.anphat.data.AppPreference;

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
