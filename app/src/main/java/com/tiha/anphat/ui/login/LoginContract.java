package com.tiha.anphat.ui.login;

import com.tiha.anphat.data.entities.UserLoginInfo;

public interface LoginContract {
    interface View {
        void onCheckLoginSuccess(UserLoginInfo info);

        void onCheckLoginError(String error);

    }

    interface Presenter {
        void CheckLogin(String userName, String pass);
    }
}
