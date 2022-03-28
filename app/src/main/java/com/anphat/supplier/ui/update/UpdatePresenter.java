package com.anphat.supplier.ui.update;

import com.anphat.supplier.data.entities.ChangeLogInFo;
import com.anphat.supplier.data.network.update.IUpdateModel;
import com.anphat.supplier.data.network.update.UpdateModel;

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
