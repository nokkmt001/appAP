package com.tiha.admin.anphat.data.network.presenteruser;

import com.tiha.admin.anphat.data.entities.IntroducePerInfo;
import com.tiha.admin.anphat.data.entities.presenteruser.InsertPresenterInfo;

import java.util.List;

public interface IPresenterUserModel {
    void InsertPresenter(Integer ID,IInsertPresenterFinish listener);

    interface IInsertPresenterFinish {
        void onSuccess(InsertPresenterInfo info);

        void onError(String error);
    }

    void GetListIntroducePer(IGetListIntroducePerFinish listener);
    interface IGetListIntroducePerFinish{
        void onSuccess(List<IntroducePerInfo> list);

        void onError(String error);
    }
}
