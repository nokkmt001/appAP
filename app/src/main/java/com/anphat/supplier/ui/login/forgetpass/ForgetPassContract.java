package com.anphat.supplier.ui.login.forgetpass;

import com.anphat.supplier.data.entities.NewCustomer;

public interface ForgetPassContract {
    interface View {
        void onUpdateCustomerSuccess(NewCustomer info);

        void onUpdateCustomerError(String error);
    }

    interface Presenter {
        void UpdateCustomer(NewCustomer info);
    }
}
