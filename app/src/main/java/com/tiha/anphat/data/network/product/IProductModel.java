package com.tiha.anphat.data.network.product;

import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.condition.ProductCondition;

import java.util.List;

public interface IProductModel {
    void GetListAllProduct(IGetListAllProductFinishListener listener);

    interface IGetListAllProductFinishListener {
        void onSuccess(List<ProductInfo> list);

        void onError(String error);
    }

    void GetListProduct(ProductCondition condition, IGetListProductFinishListener listener);

    interface IGetListProductFinishListener {
        void onSuccess(List<ProductInfo> list, Integer counter);

        void onError(String error);

    }

    void GetImageFromProductID(String productID,IGetImageFromProductIDFinishListener listener);

    interface IGetImageFromProductIDFinishListener {
        void onSuccess();

        void onError(String error);
    }
}
