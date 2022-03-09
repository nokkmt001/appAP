package com.tiha.anphatsu.ui.introduce;

import com.tiha.anphatsu.data.entities.IntroducePerInfo;
import com.tiha.anphatsu.data.entities.presenteruser.InsertPresenterInfo;

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
