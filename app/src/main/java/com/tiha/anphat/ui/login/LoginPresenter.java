package com.tiha.anphat.ui.login;

import android.text.TextUtils;

import com.tiha.anphat.data.network.nguoidung.NguoiDungModel;

public class LoginPresenter implements LoginContract.Presenter {
    LoginContract.View loginView;
    NguoiDungModel nguoiDungModel;

    public LoginPresenter(LoginContract.View loginView) {
        this.loginView = loginView;
        this.nguoiDungModel = new NguoiDungModel();
    }

    @Override
    public void CheckLogin(String userName, String passWord, String serverName) {
        if (TextUtils.isEmpty(serverName)) {
            loginView.onLoginError("Bạn chưa nhập server name công ty!");
            return;
        }

        if (TextUtils.isEmpty(userName)) {
            loginView.onLoginError("Bạn chưa nhập tên đăng nhập!");
            return;
        }
        if (TextUtils.isEmpty(passWord)) {
            loginView.onLoginError("Bạn chưa nhập mật khẩu!");
            return;
        }
//        nguoiDungModel.CheckLogin(userName, passWord, new INguoiDungModel.IOnCheckLoginFinishedListener() {
//            @Override
//            public void onLoginSuccess(NguoiDungInfo nguoiDungInfo) {
//                if (nguoiDungInfo == null) {
//                    loginView.onLoginError("Tên đăng nhập hoặc mật khẩu không đúng!");
//                    return;
//                }
//                loginView.onLoginSuccess(nguoiDungInfo);
//            }
//
//            @Override
//            public void onLoginError(String error) {
//                loginView.hideProgressBar();
//            }
//        });
    }
}
