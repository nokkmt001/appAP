package com.tiha.anphat.ui.booking;

import android.view.View;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tiha.anphat.data.entities.order.ChiTietDonInfo;
import com.tiha.anphat.ui.base.BaseAdapter;

public class ProductAdapter extends BaseAdapter<ChiTietDonInfo> {

    public ProductAdapter(int layoutId, final Callback callback,
                          SwipeRefreshLayout refreshLayout) {
        super(layoutId, callback, refreshLayout);
    }

    @Override
    public void bindView(View view) {

    }

    @Override
    public void setupViews(View itemView, ChiTietDonInfo item) {

    }

    @Override
    public void setupActions(View itemView, ChiTietDonInfo item, int position) {

    }
}
