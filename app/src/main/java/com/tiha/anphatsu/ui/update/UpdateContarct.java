package com.tiha.anphatsu.ui.update;

import com.tiha.anphatsu.data.entities.ChangeLogInFo;

public interface UpdateContarct {
    interface View{
        void onGetChangeLogSuccess(ChangeLogInFo inFo);

        void onGetChangeLogError(String error);
    }

    interface Presenter{
        void GetChangeLog();
    }
}
