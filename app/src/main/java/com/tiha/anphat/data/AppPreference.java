package com.tiha.anphat.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreference {
    public static final String PREF_ServerName = "ServerName";
    public static final String PREF_IS_LOGGED_IN = "is_logged_in";
    public static final String PREF_SDT = "SDT";
    public static final String PREF_MatKhau = "MatKhau";
    public static final String PREF_NguoiDungID = "NguoiDungID";

    private SharedPreferences sharedPreferences;

    public AppPreference(Context mContext) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public String getServerName() {
        return sharedPreferences.getString(PREF_ServerName, "");
    }

    public void setServerName(String ServerName) {
        sharedPreferences.edit().putString(PREF_ServerName, ServerName).apply();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(PREF_IS_LOGGED_IN, false);
    }
    public void setLogin(boolean status) {
        sharedPreferences.edit().putBoolean(PREF_IS_LOGGED_IN, status).apply();
    }

    public String getSDT() {
        return sharedPreferences.getString(PREF_SDT, "");
    }

    public void setSDT(String tenDangNhap) {
        sharedPreferences.edit().putString(PREF_SDT, tenDangNhap).apply();
    }

    public String getMatKhau() {
        return sharedPreferences.getString(PREF_MatKhau, "");
    }

    public void setMatKhau(String matKhau) {
        sharedPreferences.edit().putString(PREF_MatKhau, matKhau).apply();
    }

    public String getNguoiDungID() {
        return sharedPreferences.getString(PREF_NguoiDungID, "0");
    }

    public void setNguoiDungID(String nguoiDungID) {
        sharedPreferences.edit().putString(PREF_NguoiDungID, nguoiDungID).apply();
    }


}
