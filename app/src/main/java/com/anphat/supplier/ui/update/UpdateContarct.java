package com.anphat.supplier.ui.update;

import com.anphat.supplier.data.entities.ChangeLogInFo;

public interface UpdateContarct {
    interface View{
        void onGetChangeLogSuccess(ChangeLogInFo inFo);

        void onGetChangeLogError(String error);
    }

    interface Presenter{
        void GetChangeLog();
    }
}
