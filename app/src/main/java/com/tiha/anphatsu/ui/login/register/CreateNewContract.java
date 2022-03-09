package com.tiha.anphatsu.ui.login.register;

import com.tiha.anphatsu.data.entities.NewCustomer;

public interface CreateNewContract {
    interface View{
        void onInsertNewCustomerSuccess(NewCustomer info);
        void onInsertNewCustomerError(String error);
    }
    interface Presenter{
        void InsertNewCustomer(NewCustomer condition);
    }
}
