package com.anphat.supplier.data.network.user;

import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.data.entities.kho.KhoInfo;

import java.util.List;

public interface IUserModel {
    void CheckPhone(String sdt, ICheckPhoneFinishListener listener);

    interface ICheckPhoneFinishListener {
        void onSuccess(NewCustomer info);

        void onError(String error);
    }

    void GetLoginByIDPassWord(String id, String passWord, IGetLoginByIDPassWord listener);

    interface IGetLoginByIDPassWord {
        void onSuccess(NewCustomer info);

        void onError(String error);
    }

    void InsertNewCustomer(NewCustomer condition, IInsertNewCustomer listener);

    interface IInsertNewCustomer {
        void onSuccess(NewCustomer info);

        void onError(String error);
    }

    void ReSendPINcode(String id, IReSendPINcode listener);

    interface IReSendPINcode {
        void onSuccess(NewCustomer info);

        void onError(String error);
    }

    void GetListKhoByUser( IGetListKhoByUserFinish listener);

    interface IGetListKhoByUserFinish {
        void onSuccess(List<KhoInfo> list);

        void onError(String error);
    }

    void UpdateCustomer(NewCustomer info,IUpdateCustomerFinish listener);
    interface IUpdateCustomerFinish{
        void onSuccess(NewCustomer info);

        void onError(String error);
    }


}
