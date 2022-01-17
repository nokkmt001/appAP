package com.tiha.anphat.ui.splash;

import com.tiha.anphat.data.entities.UserLoginInfo;

public interface SplashContract {
    interface View {
        void onCheckStatusLoginSuccess(boolean isLogin);
        void onLoginSuccess(UserLoginInfo info);
        void onLoginError(String error);
    }

    interface Presenter {
        void CheckStatusLogin();
        void CheckLogin(String username, String password);
    }

}
