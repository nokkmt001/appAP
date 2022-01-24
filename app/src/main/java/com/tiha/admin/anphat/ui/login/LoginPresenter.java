package com.tiha.admin.anphat.ui.login;

import com.tiha.admin.anphat.data.entities.UserLoginInfo;
import com.tiha.admin.anphat.data.network.user.IUserModel;
import com.tiha.admin.anphat.data.network.user.UserModel;

public class LoginPresenter implements LoginContract.Presenter {
    LoginContract.View view;
    UserModel Model;

    public LoginPresenter(LoginContract.View loginView) {
        this.view = loginView;
        this.Model = new UserModel();
    }

    @Override
    public void CheckLogin(String userName, String pass) {
        Model.CheckLogin(userName, pass, new IUserModel.ICheckLoginSuccess() {
            @Override
            public void onSuccess(UserLoginInfo info) {
                view.onCheckLoginSuccess(info);
            }

            @Override
            public void onError(String error) {
                view.onCheckLoginError(error);
            }
        });
    }
}
