package com.tiha.anphat.main;

import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.condition.CartCondition;

import java.util.List;

public interface MainContract {
    interface View {
        void onGetListAllProductSuccess(List<ProductInfo> list);

        void onGetListAllProductError(String error);

        void onInSertCartSuccess(CartCondition info);

        void onInsertCartError(String error);

        void onUpdateCartSuccess(CartCondition info);

        void onUpdateConditionError(String error);

        void onDeleteCartSuccess();

        void onDeleteCartError(String error);

        void onGetListAllCartSuccess(List<CartCondition> list);

        void onGetListAllCartError(String error);
    }

    interface Presenter {
        void GetListAllProduct();

        void InSertCart(CartCondition condition);

        void UpdateCart(CartCondition condition);

        void DeleteCart(Integer ID);

        void GetListAllCart(Integer UserID);
    }
}
