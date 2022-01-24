package com.tiha.admin.anphat.ui.update;

import com.tiha.admin.anphat.data.entities.ChangeLogInFo;

public interface UpdateContarct {
    interface View{
        void onGetChangeLogSuccess(ChangeLogInFo inFo);

        void onGetChangeLogError(String error);
    }

    interface Presenter{
        void GetChangeLog();
    }
}
