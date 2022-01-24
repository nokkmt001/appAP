package com.tiha.admin.anphat.ui.report;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.ui.base.BaseFragment;

public class ReportFragment extends BaseFragment {
    ConstraintLayout layoutMoney;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_report;
    }

    @Override
    protected void initView(View view) {
        layoutMoney = bind(view, R.id.layoutMoney);
        layoutMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(this,)
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
