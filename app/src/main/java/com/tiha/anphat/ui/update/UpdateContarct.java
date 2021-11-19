package com.tiha.anphat.ui.update;

import com.tiha.anphat.data.entities.ChangeLogInFo;

public interface UpdateContarct {
    interface View{
        void onGetChangeLogSuccess(ChangeLogInFo inFo);

        void onGetChangeLogError(String error);
    }

    interface Presenter{
        void GetChangeLog();
    }
}
