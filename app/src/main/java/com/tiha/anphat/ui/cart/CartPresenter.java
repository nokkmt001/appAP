package com.tiha.anphat.ui.cart;

import com.tiha.anphat.data.entities.CartInfo;
import com.tiha.anphat.data.entities.condition.CartCondition;
import com.tiha.anphat.data.network.cart.CartModel;
import com.tiha.anphat.data.network.cart.ICartModel;

import java.util.List;

public class CartPresenter implements CartContract.Presenter {
    CartModel model;
    CartContract.View view;

    public CartPresenter(CartContract.View view) {
        this.model = new CartModel();
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


}
