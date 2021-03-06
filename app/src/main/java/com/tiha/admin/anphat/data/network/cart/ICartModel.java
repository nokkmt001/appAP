package com.tiha.admin.anphat.data.network.cart;

import com.tiha.admin.anphat.data.entities.CartInfo;
import com.tiha.admin.anphat.data.entities.condition.CartCondition;

import java.util.List;

public interface ICartModel {

    void InsertCart(CartCondition condition, IInsertCartFinishListener listener);

    interface IInsertCartFinishListener {
        void onSuccess(CartCondition info);

        void onError(String error);
    }

    void DeleteCart(Integer ID, IDeleteCartFinishListener listener);

    interface IDeleteCartFinishListener {
        void onSuccess();

        void onError(String error);
    }

    void UpdateCart(CartCondition condition,IUpdateCartFinishListener listener);

    interface IUpdateCartFinishListener {
        void onSuccess(CartCondition info);

        void onError(String error);
    }

    void GetListAllCart(Integer UserID,IGetListAllCartFinishListener listener);

    interface IGetListAllCartFinishListener {
        void onSuccess(List<CartInfo> list);

        void onError(String error);
    }

}
