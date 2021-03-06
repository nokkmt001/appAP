package com.tiha.admin.anphat.ui.introduce;

import com.tiha.admin.anphat.data.entities.IntroducePerInfo;
import com.tiha.admin.anphat.data.entities.presenteruser.InsertPresenterInfo;

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

        void InsertIntroduce(Integer ID);
    }
}
