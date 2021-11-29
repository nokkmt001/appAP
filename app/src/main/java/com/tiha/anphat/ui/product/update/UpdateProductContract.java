package com.tiha.anphat.ui.product.update;

import com.tiha.anphat.data.entities.product.FullProductInfo;

public class UpdateProductContract {

    interface View {
        void onGetProductSuccess(FullProductInfo info);

        void onGetProductError(String error);

        void onUpdateProductSuccess(FullProductInfo info);

        void onUpdateProductError(String error);
    }

    interface Presenter {
        void GetProduct(String ID);

        void UpdateProduct(FullProductInfo info, String image);

    }
}
