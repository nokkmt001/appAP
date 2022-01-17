package com.tiha.anphat.ui.supplier;

import com.tiha.anphat.data.entities.SupplierInfo;
import com.tiha.anphat.data.entities.condition.SupplierCondition;
import com.tiha.anphat.data.network.supplier.ISupplierModel;
import com.tiha.anphat.data.network.supplier.SupplierModel;

import java.util.List;

public class SupplierPresenter implements SupplierContract.Presenter {
    SupplierModel model;
    SupplierContract.View view;

    public SupplierPresenter(SupplierContract.View view) {
        this.model = new SupplierModel();
        this.view = view;
    }

    @Override
    public void GetListSupplier(SupplierCondition condition) {
        model.GetListSupplier(condition, new ISupplierModel.IGetListSupplierFinish() {
            @Override
            public void onSuccess(List<SupplierInfo> list, long total) {
                view.onGetListSupplierSuccess(list, total);
            }

            @Override
            public void onError(String error) {
                view.onGetListSupplierError(error);
            }
        });
    }
}
