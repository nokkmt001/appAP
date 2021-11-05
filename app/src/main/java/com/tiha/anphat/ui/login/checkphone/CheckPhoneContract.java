package com.tiha.anphat.ui.login.checkphone;

import com.tiha.anphat.data.entities.NewCustomer;

public interface CheckPhoneContract {

    interface View {
        void onCheckPhoneNumberSuccess(NewCustomer info);

        void onCheckPhoneNumberError(String error);
    }
    interface Presenter {
        void CheckPhoneNumber(String sdt);
    }
}
