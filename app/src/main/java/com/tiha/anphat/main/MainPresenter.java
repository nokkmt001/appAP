package com.tiha.anphat.main;

import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.condition.CartCondition;
import com.tiha.anphat.data.network.product.IProductModel;
import com.tiha.anphat.data.network.product.ProductModel;

import java.util.List;

public class MainPresenter implements MainContract.Presenter {
    ProductModel modelProduct;
    MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.modelProduct = new ProductModel();
        this.view = view;
    }

    @Override
    public void GetListAllProduct() {
        modelProduct.GetListAllProduct(new IProductModel.IGetListAllProductFinishListener() {
            @Override
            public void onSuccess(List<ProductInfo> list) {
                view.onGetListAllProductSuccess(list);
            }

            @Override
            public void onError(String error) {
                view.onGetListAllProductError(error);
            }
        });
    }

    @Override
    public void InSertCart(CartCondition condition) {

    }

    @Override
    public void UpdateCart(CartCondition condition) {

    }

    @Override
    public void DeleteCart(Integer ID) {

    }

    @Override
    public void GetListAllCart(Integer UserID) {

    }
}
