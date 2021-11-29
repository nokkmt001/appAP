package com.tiha.anphat.ui.product.update;

import com.tiha.anphat.data.entities.product.FullProductInfo;
import com.tiha.anphat.data.network.product.IProductModel;
import com.tiha.anphat.data.network.product.ProductModel;

public class UpdateProductPresenter implements UpdateProductContract.Presenter {
    ProductModel model;
    UpdateProductContract.View view;

    public UpdateProductPresenter(UpdateProductContract.View view) {
        model = new ProductModel();
        this.view = view;
    }

    @Override
    public void GetProduct(String ID) {
        model.GetProduct(ID, new IProductModel.IGetProduct() {
            @Override
            public void onSuccess(FullProductInfo info) {
                view.onGetProductSuccess(info);
            }

            @Override
            public void onError(String error) {
                view.onGetProductError(error);
            }
        });
    }

    @Override
    public void UpdateProduct(FullProductInfo info, String image) {
        model.UpdateProduct(info, image, new IProductModel.IUpdateProductFinish() {
            @Override
            public void onSuccess(FullProductInfo info) {
                view.onUpdateProductSuccess(info);
            }

            @Override
            public void onError(String error) {
                view.onUpdateProductError(error);
            }
        });
    }
}
