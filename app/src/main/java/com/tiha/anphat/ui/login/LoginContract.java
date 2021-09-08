package com.tiha.anphat.ui.login;


import com.tiha.anphat.data.entities.NguoiDungInfo;

public interface LoginContract {
     interface View {
        void onLoginSuccess(NguoiDungInfo nguoiDungInfo);
        void onLoginError(String error);
    }

    interface Presenter {
        void CheckLogin(String username, String password, String serverName);
    }
}
