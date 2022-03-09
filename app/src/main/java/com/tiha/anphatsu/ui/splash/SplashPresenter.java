package com.tiha.anphatsu.ui.splash;

import android.content.Context;

import com.tiha.anphatsu.data.entities.NewCustomer;
import com.tiha.anphatsu.data.network.user.IUserModel;
import com.tiha.anphatsu.data.network.user.UserModel;
import com.tiha.anphatsu.data.preft.SplashModel;

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
    public void CheckLogin(String ID, String password) {
        model.GetLoginByIDPassWord(ID, password, new IUserModel.IGetLoginByIDPassWord() {
            @Override
            public void onSuccess(NewCustomer info) {
                splashView.onLoginSuccess(info);
            }

            @Override
            public void onError(String error) {
                splashView.onLoginError(error);
            }
        });
    }
}
