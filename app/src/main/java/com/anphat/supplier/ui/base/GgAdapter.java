package com.anphat.supplier.ui.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.ProductInfo;
import com.anphat.supplier.databinding.ItemCartBinding;

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
