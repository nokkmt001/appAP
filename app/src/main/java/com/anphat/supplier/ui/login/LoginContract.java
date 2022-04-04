package com.anphat.supplier.ui.login;


import com.anphat.supplier.data.entities.NewCustomer;

public interface LoginContract {
     interface View {
        void onCheckPhoneNumberSuccess(NewCustomer info);
        void onCheckPhoneNumberError(String error);

         void onCheckLoginByIDPassSuccess(NewCustomer info);
         void onCheckLoginByIDPassError(String error);

         void onInsertNewCustomerSuccess(NewCustomer info);
         void onInsertNewCustomerError(String error);

         void onResendPinCodeSuccess(NewCustomer info);
         void onResendPinCodeError(String error);
    }

    interface Presenter {
        void CheckPhoneNumber(String sdt);
        void CheckLoginByIDPass(String id,String pass);
        void InsertNewCustomer(NewCustomer condition);
        void ResendPinCode(String id);
    }
}