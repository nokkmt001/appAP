package com.anphat.supplier.ui.login;

import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.data.network.user.IUserModel;
import com.anphat.supplier.data.network.user.UserModel;

public class LoginPresenter implements LoginContract.Presenter {
    LoginContract.View view;
    UserModel Model;

    public LoginPresenter(LoginContract.View loginView) {
        this.view = loginView;
        this.Model = new UserModel();
    }


    @Override
    public void CheckPhoneNumber(String sdt) {
        Model.CheckPhone(sdt, new IUserModel.ICheckPhoneFinishListener() {
            @Override
            public void onSuccess(NewCustomer info) {
                view.onCheckPhoneNumberSuccess(info);
            }

            @Override
            public void onError(String error) {
                view.onCheckPhoneNumberError(error);
            }
        });
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

    @Override
    public void InsertNewCustomer(NewCustomer condition) {
        Model.InsertNewCustomer(condition, new IUserModel.IInsertNewCustomer() {
            @Override
            public void onSuccess(NewCustomer info) {
                view.onInsertNewCustomerSuccess(info);
            }

            @Override
            public void onError(String error) {
                view.onInsertNewCustomerError(error);
            }
        });
    }

    @Override
    public void ResendPinCode(String id) {
        Model.ReSendPINcode(id, new IUserModel.IReSendPINcode() {
            @Override
            public void onSuccess(NewCustomer info) {
                view.onResendPinCodeSuccess(info);
            }

            @Override
            public void onError(String error) {
                view.onResendPinCodeError(error);
            }
        });
    }
}
