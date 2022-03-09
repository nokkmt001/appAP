package com.tiha.anphatsu.ui.splash;

import com.tiha.anphatsu.data.entities.NewCustomer;

public interface SplashContract {
    interface View {
        void onCheckStatusLoginSuccess(boolean isLogin);
        void onLoginSuccess(NewCustomer info);
        void onLoginError(String error);
    }

    interface Presenter {
        void CheckStatusLogin();
        void CheckLogin(String username, String password);
    }

}
