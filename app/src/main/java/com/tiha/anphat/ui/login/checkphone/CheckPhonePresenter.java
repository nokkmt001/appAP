package com.tiha.anphat.ui.login.checkphone;

import com.tiha.anphat.data.network.nguoidung.IUserModel;
import com.tiha.anphat.data.network.nguoidung.UserModel;

public class CheckPhonePresenter implements CheckPhoneContract.Presenter {
    UserModel Model;
    CheckPhoneContract.View view;

    public CheckPhonePresenter(CheckPhoneContract.View view) {
        this.view = view;
        this.Model = new UserModel();
    }

    @Override
    public void CheckPhoneNumber(String sdt) {
        Model.CheckPhone(sdt, new IUserModel.ICheckPhoneFinishListener() {
            @Override
            public void onSuccess() {
                view.onCheckPhoneNumberSuccess();
            }

            @Override
            public void onError(String error) {
                view.onCheckPhoneNumberError(error);
            }
        });
    }
}
