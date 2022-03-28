package com.anphat.supplier.data.network.update;


import com.anphat.supplier.data.entities.ChangeLogInFo;

public interface IUpdateModel {
    void GetUrlUpdate(IGetUrlUpdate listener);
    interface IGetUrlUpdate{
        void onSuccess(ChangeLogInFo inFo);

        void onError(String error);
    }
}
