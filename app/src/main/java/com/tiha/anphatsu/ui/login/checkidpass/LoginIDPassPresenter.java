package com.tiha.anphatsu.ui.login.checkidpass;

import com.tiha.anphatsu.data.entities.NewCustomer;
import com.tiha.anphatsu.data.network.user.IUserModel;
import com.tiha.anphatsu.data.network.user.UserModel;

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
