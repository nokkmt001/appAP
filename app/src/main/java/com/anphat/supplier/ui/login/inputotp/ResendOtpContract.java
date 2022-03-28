package com.anphat.supplier.ui.login.inputotp;

import com.anphat.supplier.data.entities.NewCustomer;

public interface ResendOtpContract {

    interface View {
        void onResendOtpSuccess(NewCustomer info);

        void onResendOtpError(String error);
    }

    interface Presenter {
        void ResendOtp(Integer ID);
    }
}
