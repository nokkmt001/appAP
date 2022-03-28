package com.anphat.supplier.ui.login.inputotp;

import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.data.network.user.IUserModel;
import com.anphat.supplier.data.network.user.UserModel;

public class ResendOtpPresenter implements ResendOtpContract.Presenter {
    UserModel model;
    ResendOtpContract.View view;

    public ResendOtpPresenter(ResendOtpContract.View view) {
        this.view = view;
        this.model = new UserModel();
    }

    @Override
    public void ResendOtp(Integer ID) {
        model.ReSendPINcode(String.valueOf(ID), new IUserModel.IReSendPINcode() {
            @Override
            public void onSuccess(NewCustomer info) {
                view.onResendOtpSuccess(info);
            }

            @Override
            public void onError(String error) {
                view.onResendOtpError(error);
            }
        });
    }
}
