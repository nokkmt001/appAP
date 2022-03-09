package com.tiha.anphatsu.ui.login.checkidpass;

import com.tiha.anphatsu.data.entities.NewCustomer;

public interface LoginIDPassContract {
    interface View{
        void onCheckLoginByIDPassSuccess(NewCustomer info);
        void onCheckLoginByIDPassError(String error);
    }

    interface Presenter {
        void CheckLoginByIDPass(String id,String pass);
    }
}
