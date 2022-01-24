package com.tiha.admin.anphat.data.network.update;


import com.tiha.admin.anphat.data.entities.ChangeLogInFo;

public interface IUpdateModel {
    void GetUrlUpdate(IGetUrlUpdate listener);
    interface IGetUrlUpdate{
        void onSuccess(ChangeLogInFo inFo);

        void onError(String error);
    }
}
