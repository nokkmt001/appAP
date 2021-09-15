package com.tiha.anphat.ui.login.checkphone;

public interface CheckPhoneContract {

    interface View {
        void onCheckPhoneNumberSuccess();

        void onCheckPhoneNumberError(String error);
    }
    interface Presenter {
        void CheckPhoneNumber(String sdt);
    }
}
