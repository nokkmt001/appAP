package com.tiha.admin.anphat.ui.product;

import com.tiha.admin.anphat.data.entities.ProductInfo;
import com.tiha.admin.anphat.data.entities.condition.InventoryCondition;
import com.tiha.admin.anphat.data.entities.condition.ProductCondition;
import com.tiha.admin.anphat.data.network.cart.CartModel;
import com.tiha.admin.anphat.data.network.product.IProductModel;
import com.tiha.admin.anphat.data.network.product.ProductModel;

import java.util.List;

public class ProductPresenter implements ProductContract.Presenter {
    ProductModel model;
    ProductContract.View view;
    CartModel modelCart;

    public ProductPresenter(ProductContract.View view) {
        this.view = view;
        this.model = new ProductModel();
        this.modelCart = new CartModel();
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

    @Override
    public void GetImageByProDuctID(String ID) {
        model.GetImageFromProductID(ID, new IProductModel.IGetImageFromProductIDFinishListener() {
            @Override
            public void onSuccess(String imageBitmap) {
                view.onGetImageByProDuctIDSuccess(imageBitmap);
            }
            @Override
            public void onError(String error) {
                view.onGetImageByProDuctIDError(error);
            }
        });
    }

    @Override
    public void GetProductInventory(String maKho, String productID, String date) {
        model.GetProductInventory(maKho, productID, date, new IProductModel.IGetProductInventoryFinish() {
            @Override
            public void onSuccess(Double result) {
                view.onGetProductInventorySuccess(result);
            }
            @Override
            public void onError(String error) {
                view.onGetProductInventoryError(error);
            }
        });
    }

    @Override
    public void GetListProductInventory(InventoryCondition condition) {
        model.GetListProductInventory(condition, new IProductModel.IGetAllProductInventoryFinish() {
            @Override
            public void onSuccess(List<ProductInfo> list) {
                view.onGetListProductInventorySuccess(list);
            }
            @Override
            public void onError(String error) {
                view.onGetListProductInventoryError(error);
            }
        });
    }
}
