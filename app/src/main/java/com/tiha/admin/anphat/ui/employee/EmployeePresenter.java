package com.tiha.admin.anphat.ui.employee;

import com.tiha.admin.anphat.data.entities.EmployeeInfo;
import com.tiha.admin.anphat.data.network.user.IUserModel;
import com.tiha.admin.anphat.data.network.user.UserModel;

import java.util.List;

public class EmployeePresenter implements EmployeeContract.Presenter {
    UserModel model;
    EmployeeContract.View view;

    public EmployeePresenter(EmployeeContract.View view) {
        this.view = view;
        this.model = new UserModel();
    }

    @Override
    public void GetListEmployee(String MCN) {
        model.GetListEmployee(MCN, new IUserModel.IGetListEmployeeFinish() {
            @Override
            public void onSuccess(List<EmployeeInfo> info) {
                view.onGetListEmployeeSuccess(info);
            }
            @Override
            public void onError(String error) {
                view.onGetListEmployeeError(error);
            }
        });
    }
}
