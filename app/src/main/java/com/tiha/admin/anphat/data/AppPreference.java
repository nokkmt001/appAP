package com.tiha.admin.anphat.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreference {
    public static final String PREF_ServerName = "ServerName";
    public static final String PREF_IS_LOGGED_IN = "is_logged_in";
    public static final String PREF_SDT = "SDT";
    public static final String PREF_MatKhau = "MatKhau";
    public static final String PREF_UserName = "UserName";
    public static final String PREF_USER = "user";
    public static final String PREF_BOOKING = "booking";
    public static final String PREF_IS_INPUT_OTP="otp";

    private SharedPreferences sharedPreferences;

    public AppPreference(Context mContext) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public String getUser(){
        return sharedPreferences.getString(PREF_USER,"");
    }

    public void setUser(String user){
        sharedPreferences.edit().putString(PREF_USER,user).apply();
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

    public boolean isOtp() {
        return sharedPreferences.getBoolean(PREF_IS_INPUT_OTP, false);
    }
    public void setOtp(boolean status) {
        sharedPreferences.edit().putBoolean(PREF_IS_INPUT_OTP, status).apply();
    }

    public String getSDT() {
        return sharedPreferences.getString(PREF_SDT, "");
    }

    public void setSDT(String tenDangNhap) {
        sharedPreferences.edit().putString(PREF_SDT, tenDangNhap).apply();
    }

    public String getPassWord() {
        return sharedPreferences.getString(PREF_MatKhau, "");
    }

    public void setPassWord(String matKhau) {
        sharedPreferences.edit().putString(PREF_MatKhau, matKhau).apply();
    }

    public String getUserName() {
        return sharedPreferences.getString(PREF_UserName, "0");
    }

    public void setUserName(String nguoiDungID) {
        sharedPreferences.edit().putString(PREF_UserName, nguoiDungID).apply();
    }

    public String getBooking() {
        return sharedPreferences.getString(PREF_BOOKING, "");
    }

    public void setBooking(String matKhau) {
        sharedPreferences.edit().putString(PREF_BOOKING, matKhau).apply();
    }

}
