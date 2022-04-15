package com.anphat.supplier.ui.home;

import com.anphat.supplier.data.entities.BannerInfo;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.condition.CartCondition;

import java.util.List;

public interface HomeContract {
    interface View {
        void onGetListProductSuccess(List<ProductNew> list);

        void onGetListProductError(String error);

        void onInsertCartSuccess(CartCondition info);

        void onInsertCartError(String error);

        void onGetListBannerSuccess(List<BannerInfo> list);

        void onGetListBannerError(String error);

        void onGetListProductPromotionSuccess(List<ProductNew> list);

        void onGetListProductPromotionError(String error);

    }

    interface Presenter {
        void GetListProduct(String url);

        void GetListProductPromotion(String url);

        void InsertCart(CartCondition condition);

        void GetListBanner(String url);

    }
}
