package com.tiha.anphat.data.network.supplier;

import com.tiha.anphat.data.entities.SupplierInfo;
import com.tiha.anphat.data.entities.condition.SupplierCondition;

import java.util.List;

public interface ISupplierModel {
    void GetListSupplier(SupplierCondition condition, IGetListSupplierFinish listener);

    interface IGetListSupplierFinish {
        void onSuccess(List<SupplierInfo> list, long total);

        void onError(String error);
    }

    void GetSupplier(String supplierID,ISupplierFinish listener);

    void InsertSupplier(SupplierInfo info, ISupplierFinish listener);

    void UpdateSupplier(SupplierInfo info, ISupplierFinish listener);

    interface ISupplierFinish {
        void onSuccess(SupplierInfo info);

        void onError(String error);
    }

    void DeleteSupplier(String MKH, IDeleteSupplierFinish listener);

    interface IDeleteSupplierFinish {
        void onSuccess();

        void onError(String error);
    }
}
