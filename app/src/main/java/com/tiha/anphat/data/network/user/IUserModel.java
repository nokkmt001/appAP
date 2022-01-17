package com.tiha.anphat.data.network.user;

import com.tiha.anphat.data.entities.EmployeeInfo;
import com.tiha.anphat.data.entities.NewCustomer;
import com.tiha.anphat.data.entities.UserLoginInfo;
import com.tiha.anphat.data.entities.kho.KhoInfo;

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

    void GetListKhoByUser(IGetListKhoByUserFinish listener);

    interface IGetListKhoByUserFinish {
        void onSuccess(List<KhoInfo> list);

        void onError(String error);
    }

    void CheckLogin(String userName, String pass, ICheckLoginSuccess listener);

    interface ICheckLoginSuccess {
        void onSuccess(UserLoginInfo info);

        void onError(String error);
    }

    void GetListEmployee(String chiNhanhID, IGetListEmployeeFinish listener);

    interface IGetListEmployeeFinish {
        void onSuccess(List<EmployeeInfo> info);

        void onError(String error);
    }


}
