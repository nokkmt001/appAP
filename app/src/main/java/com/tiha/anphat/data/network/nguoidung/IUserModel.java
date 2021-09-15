package com.tiha.anphat.data.network.nguoidung;

import com.tiha.anphat.data.entities.NewCustomer;

public interface IUserModel {
    void CheckPhone(String sdt, ICheckPhoneFinishListener listener);

    interface ICheckPhoneFinishListener {
        void onSuccess();

        void onError(String error);
    }

    void GetLoginByIDPassWord(String id, String passWord, IGetLoginByIDPassWord listener);

    interface IGetLoginByIDPassWord {
        void onSuccess(NewCustomer info);

        void onError(String error);
    }

    void InsertNewCustomer(NewCustomer condition,IInsertNewCustomer listener);
    interface IInsertNewCustomer{
        void onSuccess(NewCustomer info);

        void onError(String error);
    }

    void ReSendPINcode(String id,IReSendPINcode listener);
    interface IReSendPINcode{
        void onSuccess(NewCustomer info);

        void onError(String error);
    }
}
