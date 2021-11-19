package com.tiha.anphat.ui.update;

import com.tiha.anphat.data.entities.ChangeLogInFo;
import com.tiha.anphat.data.network.update.IUpdateModel;
import com.tiha.anphat.data.network.update.UpdateModel;

public class UpdatePresenter implements UpdateContarct.Presenter {
    UpdateModel model;
    UpdateContarct.View view;

    public UpdatePresenter(UpdateContarct.View view) {
        this.model = new UpdateModel();
        this.view = view;
    }

    @Override
    public void GetChangeLog() {
        model.GetUrlUpdate(new IUpdateModel.IGetUrlUpdate() {
            @Override
            public void onSuccess(ChangeLogInFo inFo) {
                view.onGetChangeLogSuccess(inFo);
            }

            @Override
            public void onError(String error) {
                view.onGetChangeLogError(error);
            }
        });
    }
}
