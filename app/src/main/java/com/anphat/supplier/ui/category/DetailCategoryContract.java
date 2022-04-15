package com.anphat.supplier.ui.category;

import com.anphat.supplier.data.entities.ProductNew;

import java.util.List;

public interface DetailCategoryContract {

    interface View {
        void onGetListProductSuccess(List<ProductNew> list);

        void onGetListProductError(String error);

    }

    interface Presenter {
        void GetListProduct(String url);
    }
}
