package com.tiha.admin.anphat.ui.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.data.entities.ProductInfo;
import com.tiha.admin.anphat.databinding.ItemCartBinding;

public class GgAdapter extends BaseTestAdapter<ProductInfo, ItemCartBinding> {
    @Override
    public void setupViews(View itemView, ProductInfo item, int position) {
        binding.imageAdd.setBackgroundResource(R.color.Red);
        binding.textTitle.setText(item.getProduct_ID());
    }
    @Override
    public ItemCartBinding getViewBinding(ViewGroup parent, int viewType) {
        return ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()));
    }
}
