package com.tiha.anphat.ui.supplier;

import com.tiha.anphat.data.entities.SupplierInfo;
import com.tiha.anphat.data.entities.condition.SupplierCondition;

import java.util.List;

public interface SupplierContract {
    interface View{
       void onGetListSupplierSuccess(List<SupplierInfo> list, long total);

       void onGetListSupplierError(String error);
    }

    interface Presenter{
        void GetListSupplier(SupplierCondition condition);
    }
}
