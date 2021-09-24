package com.tiha.anphat.ui.cart;

import com.tiha.anphat.data.entities.CartInfo;
import com.tiha.anphat.data.entities.condition.CartCondition;

import java.util.List;

public interface CartContract {
    interface View{
        void onGetListAllCartSuccess(List<CartInfo> list);

        void onGetListAllCartError(String error);

        void onUpdateCartSuccess(CartCondition info);

        void onUpdateCartError(String error);

        void onDeleteCartSuccess();

        void onDeleteCartError(String error);
    }

    interface Presenter{
        void GetListCart(Integer UserID);

        void UpdateCart(CartCondition condition);

        void DeleteCart(Integer ID);
    }
}
