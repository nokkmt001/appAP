package com.tiha.anphatsu.data.network.update;


import com.tiha.anphatsu.data.entities.ChangeLogInFo;

public interface IUpdateModel {
    void GetUrlUpdate(IGetUrlUpdate listener);
    interface IGetUrlUpdate{
        void onSuccess(ChangeLogInFo inFo);

        void onError(String error);
    }
}
