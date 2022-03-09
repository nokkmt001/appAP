package com.tiha.anphatsu.ui.product.full;

import com.tiha.anphatsu.data.entities.ProductNew;

public interface DetailPrContract {

    interface View {
        void onGetProductSuccess(ProductNew info);

        void onGetProductError(String error);
    }

    interface Presenter {
        void GetProduct(String ID);
    }
}
