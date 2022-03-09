package com.tiha.anphatsu.ui.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiha.anphatsu.R;
import com.tiha.anphatsu.data.entities.ProductInfo;
import com.tiha.anphatsu.databinding.ItemCartBinding;

public class GgAdapter extends BaseTestAdapter<ProductInfo, ItemCartBinding> {
    ItemCartBinding binding;   // ok rồi đó

    @Override
    public void setupViews(View itemView, ProductInfo item, int position) {
        binding.imageAdd.setBackgroundResource(R.color.Red);
        binding.textTitle.setText(item.getProduct_ID());
    }

    @Override
    public ItemCartBinding getViewBinding(ViewGroup parent, int viewType) {
        return binding = ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()));
    }
}
