package com.anphat.supplier.ui.splash;

import com.anphat.supplier.data.entities.FCMMobileInfo;

public interface FcmContract {

    interface View{
        void onInsertFCMSuccess(FCMMobileInfo info);

        void onInsertFCMError(String error);

        void onGetFCMSuccess(FCMMobileInfo info);

        void onGetFCMError(String error);
    }
    interface Presenter{
        void insertFCM(FCMMobileInfo info);

        void getFCM(String token);
    }

}
