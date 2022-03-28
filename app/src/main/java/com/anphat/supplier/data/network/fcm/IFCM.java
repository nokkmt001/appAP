package com.anphat.supplier.data.network.fcm;

import com.anphat.supplier.data.entities.FCMMobileInfo;

public interface IFCM {

    void InsertFCMMobile(FCMMobileInfo fcmMobileInfo, IFinishedListener listener);

    void GetFCMFromToken(String token, IFinishedListener listener);

    interface IFinishedListener {
        void onInsertToKenSuccess(FCMMobileInfo fcmMobileInfo);

        void onInsertToKenError(String error);
    }
}
