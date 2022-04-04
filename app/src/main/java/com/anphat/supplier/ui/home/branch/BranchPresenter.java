package com.anphat.supplier.ui.home.branch;

import com.anphat.supplier.data.entities.kho.KhoInfo;
import com.anphat.supplier.data.network.user.IUserModel;
import com.anphat.supplier.data.network.user.UserModel;

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