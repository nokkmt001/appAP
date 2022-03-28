package com.anphat.supplier.ui.home.branch;

import com.anphat.supplier.data.entities.kho.KhoInfo;

import java.util.List;

public interface BranchContract {
    interface View {
        void onGetListBranchSuccess(List<KhoInfo> list);

        void onGetListBranchError(String error);
    }

    interface Presenter {
        void GetListBranch();
    }
}
