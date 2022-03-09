package com.tiha.anphatsu.ui.home;

import com.tiha.anphatsu.data.entities.ProductNew;
import com.tiha.anphatsu.data.entities.condition.CartCondition;

import java.util.List;

public interface HomeContract {
    interface View {
        void onGetListProductSuccess(List<ProductNew> list);

        void onGetListProductError(String error);

        void onInsertCartSuccess(CartCondition info);

        void onInsertCartError(String error);
    }

    interface Presenter {
        void GetListProduct(String url);

        void InsertCart(CartCondition condition);

    }
}
