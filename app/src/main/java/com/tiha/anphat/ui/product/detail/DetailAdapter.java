package com.tiha.anphat.ui.product.detail;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.ui.base.BaseAdapter;

import java.util.List;

public class DetailAdapter extends BaseAdapter {
    public DetailAdapter(Context context, List<ProductInfo> list) {
        super(context);
        layout_id = R.layout.layout_header;
        dataList = list;
    }

    @Override
    public void getView(View view) {
        TextView tv;
        tv = view.findViewById(R.id.inputSearch);
    }

    @Override
    public void onBindViewHold(int position, Object itemView) {
        if (itemView != null) {

        }
    }
}
