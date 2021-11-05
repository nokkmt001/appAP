package com.tiha.anphat.main;

import com.tiha.anphat.data.entities.CartInfo;
import com.tiha.anphat.data.entities.CategoryInfo;
import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.condition.CartCondition;
import com.tiha.anphat.data.entities.kho.KhoInfo;

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

        void onGetListAllCartSuccess(List<CartInfo> list);

        void onGetListAllCartError(String error);

        void onGetListKhoSuccess(List<KhoInfo> list);

        void onGetListKhoError(String error);

        void onGetCategorySuccess(List<CategoryInfo>list);

        void onGetCategoryError(String error);
    }

    interface Presenter {
        void GetListAllProduct();

        void InSertCart(CartCondition condition);

        void UpdateCart(CartCondition condition);

        void DeleteCart(Integer ID);

        void GetListAllCart(Integer UserID);

        void GetListKho();

        void GetCategory();
    }
}
