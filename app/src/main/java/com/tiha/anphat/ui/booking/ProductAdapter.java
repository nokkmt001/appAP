package com.tiha.anphat.ui.booking;

import android.view.View;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.order.ChiTietDonInfo;
import com.tiha.anphat.ui.base.BaseAdapter;

public class ProductAdapter extends BaseAdapter<ChiTietDonInfo> {
    @Override
    public int getLayoutId() {
        return R.layout.item_product;
    }

    @Override
    public void bind(View view) {

    }

    @Override
    public void setupViews(View itemView, ChiTietDonInfo item, int position) {

    }


    @Override
    public void setupActions(View itemView, ChiTietDonInfo item, int position) {

    }
}
