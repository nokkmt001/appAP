package com.anphat.supplier.ui.introduce;

import com.anphat.supplier.data.entities.IntroducePerInfo;
import com.anphat.supplier.data.entities.presenteruser.InsertPresenterInfo;

import java.util.List;

public interface IntroduceContract {

    interface View {
        void onGetListIntroduceSuccess(List<IntroducePerInfo> list);

        void onGetListIntroduceError(String error);

        void onInsertIntroduceSuccess(InsertPresenterInfo info);

        void onInsertIntroduceError(String error);
    }

    interface Presenter {
        void GetListIntroduce();

        void InsertIntroduce(String ID);
    }
}
