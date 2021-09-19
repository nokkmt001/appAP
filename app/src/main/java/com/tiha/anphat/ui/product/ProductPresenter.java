package com.tiha.anphat.ui.product;

import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.condition.ProductCondition;
import com.tiha.anphat.data.network.product.IProductModel;
import com.tiha.anphat.data.network.product.ProductModel;

import java.util.List;

public class ProductPresenter implements ProductContract.Presenter {
    ProductModel model;
    ProductContract.View view;

    public ProductPresenter(ProductContract.View view) {
        this.view = view;
        this.model = new ProductModel();
    }

    @Override
    public void GetListProduct(ProductCondition condition) {
        model.GetListProduct(condition, new IProductModel.IGetListProductFinishListener() {
            @Override
            public void onSuccess(List<ProductInfo> list, Integer counter) {
                view.onGetListProductSuccess(list, counter);
            }

            @Override
            public void onError(String error) {
                view.onGetListProductError(error);
            }
        });
    }
}
