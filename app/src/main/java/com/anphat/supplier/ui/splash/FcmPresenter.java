package com.anphat.supplier.ui.splash;

import com.anphat.supplier.data.entities.FCMMobileInfo;
import com.anphat.supplier.data.network.fcm.FCM;
import com.anphat.supplier.data.network.fcm.IFCM;

public class FcmPresenter implements FcmContract.Presenter {
    FCM model;
    FcmContract.View view;

    public FcmPresenter(FcmContract.View view) {
        this.view = view;
        this.model = new FCM();
    }

    @Override
    public void insertFCM(FCMMobileInfo info) {
        model.InsertFCMMobile(info, new IFCM.IFinishedListener() {
            @Override
            public void onInsertToKenSuccess(FCMMobileInfo fcmMobileInfo) {
                view.onInsertFCMSuccess(fcmMobileInfo);
            }

            @Override
            public void onInsertToKenError(String error) {
                view.onInsertFCMError(error);
            }
        });
    }

    @Override
    public void getFCM(String token) {
        model.GetFCMFromToken(token, new IFCM.IFinishedListener() {
            @Override
            public void onInsertToKenSuccess(FCMMobileInfo fcmMobileInfo) {
                view.onGetFCMSuccess(fcmMobileInfo);
            }

            @Override
            public void onInsertToKenError(String error) {
                view.onGetFCMError(error);
            }
        });

    }

}
