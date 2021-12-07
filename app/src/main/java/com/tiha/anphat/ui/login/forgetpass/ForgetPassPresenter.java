package com.tiha.anphat.ui.login.forgetpass;

import com.tiha.anphat.data.entities.NewCustomer;
import com.tiha.anphat.data.network.user.IUserModel;
import com.tiha.anphat.data.network.user.UserModel;

public class ForgetPassPresenter implements ForgetPassContract.Presenter {
    UserModel model;
    ForgetPassContract.View view;

    public ForgetPassPresenter(ForgetPassContract.View view) {
        this.view = view;
        this.model = new UserModel();
    }

    @Override
    public void UpdateCustomer(NewCustomer info) {
        model.UpdateCustomer(info, new IUserModel.IUpdateCustomerFinish() {
            @Override
            public void onSuccess(NewCustomer info) {
                view.onUpdateCustomerSuccess(info);
            }

            @Override
            public void onError(String error) {
                view.onUpdateCustomerError(error);
            }
        });
    }
}
