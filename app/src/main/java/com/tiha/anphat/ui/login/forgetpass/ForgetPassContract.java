package com.tiha.anphat.ui.login.forgetpass;

import com.tiha.anphat.data.entities.NewCustomer;

public interface ForgetPassContract {
    interface View {
        void onUpdateCustomerSuccess(NewCustomer info);

        void onUpdateCustomerError(String error);
    }

    interface Presenter {
        void UpdateCustomer(NewCustomer info);
    }
}
