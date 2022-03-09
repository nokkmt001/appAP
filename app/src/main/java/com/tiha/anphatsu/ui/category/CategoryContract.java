package com.tiha.anphatsu.ui.category;

import com.tiha.anphatsu.data.entities.CategoryNew;

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
