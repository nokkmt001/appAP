package com.tiha.anphat.ui.product;

import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.condition.CartCondition;
import com.tiha.anphat.data.entities.condition.InventoryCondition;
import com.tiha.anphat.data.entities.condition.ProductCondition;
import com.tiha.anphat.data.network.cart.CartModel;
import com.tiha.anphat.data.network.cart.ICartModel;
import com.tiha.anphat.data.network.product.IProductModel;
import com.tiha.anphat.data.network.product.ProductModel;

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
    public void InsertCart(CartCondition condition) {
        modelCart.InsertCart(condition, new ICartModel.IInsertCartFinishListener() {
            @Override
            public void onSuccess(CartCondition info) {
                view.onInsertCartSuccess(info);
            }

            @Override
            public void onError(String error) {
                view.onInsertCartError(error);
            }
        });
    }

    @Override
    public void GetProductInventory(String maKho, String productID, String date) {
        model.GetProductInventory(maKho, productID, date, new IProductModel.IGetProductInventoryFinish() {
            @Override
            public void onSuccess(Integer result) {
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
