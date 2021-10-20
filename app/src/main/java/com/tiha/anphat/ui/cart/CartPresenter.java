package com.tiha.anphat.ui.cart;

import com.tiha.anphat.data.entities.CartInfo;
import com.tiha.anphat.data.entities.condition.CartCondition;
import com.tiha.anphat.data.entities.condition.ProductPriceCondition;
import com.tiha.anphat.data.network.cart.CartModel;
import com.tiha.anphat.data.network.cart.ICartModel;
import com.tiha.anphat.data.network.product.IProductModel;
import com.tiha.anphat.data.network.product.ProductModel;

import java.util.List;

public class CartPresenter implements CartContract.Presenter {
    CartModel model;
    ProductModel modelProduct;
    CartContract.View view;

    public CartPresenter(CartContract.View view) {
        this.model = new CartModel();
        this.modelProduct = new ProductModel();
        this.view = view;
    }

    @Override
    public void GetListCart(Integer UserID) {
        model.GetListAllCart(UserID, new ICartModel.IGetListAllCartFinishListener() {
            @Override
            public void onSuccess(List<CartInfo> list) {
                view.onGetListAllCartSuccess(list);
            }

            @Override
            public void onError(String error) {
                view.onGetListAllCartError(error);
            }
        });
    }

    @Override
    public void UpdateCart(CartCondition condition) {
        model.UpdateCart(condition, new ICartModel.IUpdateCartFinishListener() {
            @Override
            public void onSuccess(CartCondition info) {
                view.onUpdateCartSuccess(info);
            }

            @Override
            public void onError(String error) {
                view.onUpdateCartError(error);
            }
        });
    }

    @Override
    public void DeleteCart(Integer ID) {
        model.DeleteCart(ID, new ICartModel.IDeleteCartFinishListener() {
            @Override
            public void onSuccess() {
                view.onDeleteCartSuccess();
            }

            @Override
            public void onError(String error) {
                view.onDeleteCartError(error);
            }
        });
    }

    @Override
    public void GetProductPriceByUser(ProductPriceCondition condition) {
        modelProduct.GetProductPriceByUserID(condition, new IProductModel.IGetProductPriceByUserIDListener() {
            @Override
            public void onSuccess(Double price) {
                view.onGetDonGiaProductByUserSuccess(price);
            }

            @Override
            public void onError(String error) {
                view.onGetDonGiaProductByUserError(error);
            }
        });
    }

    @Override
    public void GetProductInventory(String maKho, String productID, String date) {
        modelProduct.GetProductInventory(maKho, productID, date, new IProductModel.IGetProductInventoryFinish() {
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
}
