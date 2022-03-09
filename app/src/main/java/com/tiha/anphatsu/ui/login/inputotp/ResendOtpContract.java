package com.tiha.anphatsu.ui.login.inputotp;

import com.tiha.anphatsu.data.entities.NewCustomer;

public interface ResendOtpContract {

    interface View {
        void onResendOtpSuccess(NewCustomer info);

        void onResendOtpError(String error);
    }

    interface Presenter {
        void ResendOtp(Integer ID);
    }
}
