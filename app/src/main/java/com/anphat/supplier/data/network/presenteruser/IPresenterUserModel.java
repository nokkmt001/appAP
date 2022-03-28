package com.anphat.supplier.data.network.presenteruser;

import com.anphat.supplier.data.entities.IntroducePerInfo;
import com.anphat.supplier.data.entities.presenteruser.InsertPresenterInfo;

import java.util.List;

public interface IPresenterUserModel {
    void InsertPresenter(String ID,IInsertPresenterFinish listener);

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
