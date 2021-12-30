package com.tiha.anphat.ui.report;

import android.content.Intent;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.tiha.anphat.R;
import com.tiha.anphat.ui.base.BaseFragment;

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
