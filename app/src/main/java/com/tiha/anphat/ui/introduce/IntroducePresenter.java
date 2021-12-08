package com.tiha.anphat.ui.introduce;

import com.tiha.anphat.data.entities.IntroducePerInfo;
import com.tiha.anphat.data.entities.presenteruser.InsertPresenterInfo;
import com.tiha.anphat.data.network.presenteruser.IPresenterUserModel;
import com.tiha.anphat.data.network.presenteruser.PresenterUserModel;

import java.util.List;

public class IntroducePresenter implements IntroduceContract.Presenter {
    PresenterUserModel model;
    IntroduceContract.View view;

    public IntroducePresenter(IntroduceContract.View view) {
        this.view = view;
        this.model = new PresenterUserModel();
    }

    @Override
    public void GetListIntroduce() {
        model.GetListIntroducePer(new IPresenterUserModel.IGetListIntroducePerFinish() {
            @Override
            public void onSuccess(List<IntroducePerInfo> list) {
                view.onGetListIntroduceSuccess(list);
            }

            @Override
            public void onError(String error) {
                view.onGetListIntroduceError(error);
            }
        });
    }

    @Override
    public void InsertIntroduce(String sdt) {
        model.InsertPresenter(sdt, new IPresenterUserModel.IInsertPresenterFinish() {
            @Override
            public void onSuccess(InsertPresenterInfo info) {
                view.onInsertIntroduceSuccess(info);
            }

            @Override
            public void onError(String error) {
                view.onInsertIntroduceError(error);
            }
        });
    }
}
