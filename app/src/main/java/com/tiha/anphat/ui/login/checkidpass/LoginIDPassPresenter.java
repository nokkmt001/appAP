package com.tiha.anphat.ui.login.checkidpass;

import com.tiha.anphat.data.entities.NewCustomer;
import com.tiha.anphat.data.network.nguoidung.IUserModel;
import com.tiha.anphat.data.network.nguoidung.UserModel;

public class LoginIDPassPresenter implements LoginIDPassContract.Presenter {
    LoginIDPassContract.View view;
    UserModel Model;

    public LoginIDPassPresenter(LoginIDPassContract.View loginView) {
        this.view = loginView;
        this.Model = new UserModel();
    }
    @Override
    public void CheckLoginByIDPass(String id, String pass) {
        Model.GetLoginByIDPassWord(id, pass, new IUserModel.IGetLoginByIDPassWord() {
            @Override
            public void onSuccess(NewCustomer info) {
                view.onCheckLoginByIDPassSuccess(info);
            }

            @Override
            public void onError(String error) {
                view.onCheckLoginByIDPassError(error);
            }
        });
    }
}
