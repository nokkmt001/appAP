package com.tiha.anphat.ui.login.inputotp;

import com.tiha.anphat.data.entities.NewCustomer;

public interface ResendOtpContract {

    interface View {
        void onResendOtpSuccess(NewCustomer info);

        void onResendOtpError(String error);
    }

    interface Presenter {
        void ResendOtp(Integer ID);
    }
}
