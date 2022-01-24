package com.tiha.admin.anphat.ui.home.branch;

import com.tiha.admin.anphat.data.entities.kho.KhoInfo;
import com.tiha.admin.anphat.data.network.user.IUserModel;
import com.tiha.admin.anphat.data.network.user.UserModel;

import java.util.List;

public class BranchPresenter implements BranchContract.Presenter {
    UserModel model;
    BranchContract.View view;

    public BranchPresenter(BranchContract.View view) {
        this.view = view;
        this.model = new UserModel();
    }

    @Override
    public void GetListBranch() {
        model.GetListKhoByUser(new IUserModel.IGetListKhoByUserFinish() {
            @Override
            public void onSuccess(List<KhoInfo> list) {
                view.onGetListBranchSuccess(list);
            }

            @Override
            public void onError(String error) {
                view.onGetListBranchError(error);
            }
        });
    }
}
