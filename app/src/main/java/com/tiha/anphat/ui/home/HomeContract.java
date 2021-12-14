package com.tiha.anphat.ui.home;

import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.condition.CartCondition;
import com.tiha.anphat.data.entities.condition.ProductCondition;

import java.util.List;

public interface HomeContract {
    interface View {
        void onGetListProductSuccess(List<ProductInfo> list, Integer total);

        void onGetListProductError(String error);

        void onInsertCartSuccess(CartCondition info);

        void onInsertCartError(String error);
    }

    interface Presenter {
        void GetListProduct(ProductCondition condition);

        void InsertCart(CartCondition condition);

    }
}
