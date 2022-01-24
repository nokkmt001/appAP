package com.tiha.admin.anphat.ui.home.branch;

import com.tiha.admin.anphat.data.entities.kho.KhoInfo;

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
