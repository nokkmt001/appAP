package com.anphat.supplier.ui.report;

import android.view.View;
import android.widget.LinearLayout;

import com.anphat.supplier.R;
import com.anphat.supplier.ui.base.BaseFragment;

public class ReportFragment extends BaseFragment {
    LinearLayout layoutReserve,layoutReportRevenue,layoutSales;
    @Override
    protected int getLayoutID() {
        return R.layout.fragment_report;
    }

    @Override
    protected void initView(View view) {
        layoutReportRevenue = bind(view,R.id.layoutReportRevenue);
        layoutReserve = bind(view,R.id.layoutReserve);
        layoutSales = bind(view,R.id.layoutSales);


    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
