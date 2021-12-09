package com.tiha.anphat.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiha.anphat.data.entities.CategoryInfo;
import com.tiha.anphat.databinding.ItemCategoryBinding;
import com.tiha.anphat.ui.base.BaseTestAdapter;

public class CategoryAdapter extends BaseTestAdapter<CategoryInfo, ItemCategoryBinding> {
    ItemCategoryBinding bd;
    @Override
    public void setupViews(View itemView, CategoryInfo item, int position) {
        bd.textCategory.setText(item.getLoaihang());
    }

    @Override
    public ItemCategoryBinding getViewBinding(ViewGroup parent, int viewType) {
        return bd = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()));
    }
}
