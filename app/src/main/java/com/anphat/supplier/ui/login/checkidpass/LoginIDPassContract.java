package com.anphat.supplier.ui.login.checkidpass;

import com.anphat.supplier.data.entities.NewCustomer;

public interface LoginIDPassContract {
    interface View{
        void onCheckLoginByIDPassSuccess(NewCustomer info);
        void onCheckLoginByIDPassError(String error);
    }

    interface Presenter {
        void CheckLoginByIDPass(String id,String pass);
    }
}
