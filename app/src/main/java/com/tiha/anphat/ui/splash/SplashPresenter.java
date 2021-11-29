package com.tiha.anphat.ui.splash;

import android.content.Context;
import android.view.View;

import com.tiha.anphat.data.entities.NewCustomer;
import com.tiha.anphat.data.entities.UserLoginInfo;
import com.tiha.anphat.data.network.user.IUserModel;
import com.tiha.anphat.data.network.user.UserModel;
import com.tiha.anphat.data.preft.SplashModel;

public class SplashPresenter implements SplashContract.Presenter {
    SplashContract.View splashView;
    Context context;
    SplashModel splashModel;
    UserModel model;

    public SplashPresenter(Context context, SplashContract.View splashView) {
        this.splashView = splashView;
        this.context = context;
        this.splashModel = new SplashModel(context);
        this.model = new UserModel();
    }

    @Override
    public void CheckStatusLogin() {
        boolean isLogin = splashModel.CheckStatusLogin();
        splashView.onCheckStatusLoginSuccess(isLogin);
    }

    @Override
    public void CheckLogin(String userName, String password) {
        model.CheckLogin(userName, password, new IUserModel.ICheckLoginSuccess() {
            @Override
            public void onSuccess(UserLoginInfo info) {
                splashView.onLoginSuccess(info);
            }

            @Override
            public void onError(String error) {
                splashView.onLoginError(error);
            }
        });
    }
}
