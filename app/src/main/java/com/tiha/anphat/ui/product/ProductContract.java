package com.tiha.anphat.ui.product;

import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.condition.ProductCondition;

import java.util.List;

public interface ProductContract {
    interface View{
        void onGetListProductSuccess(List<ProductInfo> list,Integer total);

        void onGetListProductError(String error);

        void onGetImageByProDuctIDSuccess();

        void onGetImageByProDuctIDError(String error);
    }

    interface Presenter{
        void GetListProduct(ProductCondition condition);

        void GetImageByProDuctID(String ID);
    }
}
