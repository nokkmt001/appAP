package com.tiha.anphat.data.network.update;


import com.tiha.anphat.data.entities.ChangeLogInFo;

public interface IUpdateModel {
    void GetUrlUpdate(IGetUrlUpdate listener);
    interface IGetUrlUpdate{
        void onSuccess(ChangeLogInFo inFo);

        void onError(String error);
    }
}
