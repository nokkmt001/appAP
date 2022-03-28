package com.anphat.supplier.ui.login.register;

import com.anphat.supplier.data.entities.NewCustomer;

public interface CreateNewContract {
    interface View{
        void onInsertNewCustomerSuccess(NewCustomer info);
        void onInsertNewCustomerError(String error);
    }
    interface Presenter{
        void InsertNewCustomer(NewCustomer condition);
    }
}
