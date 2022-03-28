package com.anphat.supplier.ui.login.checkphone;

import com.anphat.supplier.data.entities.NewCustomer;

public interface CheckPhoneContract {

    interface View {
        void onCheckPhoneNumberSuccess(NewCustomer info);

        void onCheckPhoneNumberError(String error);
    }
    interface Presenter {
        void CheckPhoneNumber(String sdt);
    }
}
