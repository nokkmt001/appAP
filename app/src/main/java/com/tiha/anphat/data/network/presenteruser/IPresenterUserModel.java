package com.tiha.anphat.data.network.presenteruser;

import com.tiha.anphat.data.entities.presenteruser.InsertPresenterInfo;

interface IPresenterUserModel {
    void InsertPresenter(Integer ID,IInsertPresenterFinish listener);

    interface IInsertPresenterFinish {
        void onSuccess(InsertPresenterInfo info);

        void onError(String error);
    }
}
