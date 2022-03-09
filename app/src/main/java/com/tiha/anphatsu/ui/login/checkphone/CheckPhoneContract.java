package com.tiha.anphatsu.ui.login.checkphone;

import com.tiha.anphatsu.data.entities.NewCustomer;

public interface CheckPhoneContract {

    interface View {
        void onCheckPhoneNumberSuccess(NewCustomer info);

        void onCheckPhoneNumberError(String error);
    }
    interface Presenter {
        void CheckPhoneNumber(String sdt);
    }
}
