package com.tiha.anphat.ui.login.register;

import com.tiha.anphat.data.entities.NewCustomer;
import com.tiha.anphat.data.network.user.IUserModel;
import com.tiha.anphat.data.network.user.UserModel;

public class CreateNewPresenter implements CreateNewContract.Presenter {
    CreateNewContract.View view;
    UserModel Model;

    public CreateNewPresenter(CreateNewContract.View loginView) {
        this.view = loginView;
        this.Model = new UserModel();
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
}
