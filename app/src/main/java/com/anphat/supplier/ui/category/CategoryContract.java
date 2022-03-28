package com.anphat.supplier.ui.category;

import com.anphat.supplier.data.entities.CategoryNew;

import java.util.List;

public interface CategoryContract {

    interface View {
        void onGetCategorySuccess(List<CategoryNew> list);

        void onGetCategoryError(String error);
    }

    interface Presenter{
     void GetCategory();
    }
}
