package com.tiha.admin.anphat.ui.report.money;

import android.content.Intent;
import android.view.View;

import com.tiha.admin.anphat.ui.report.money.spend.SpendActivity;
import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.databinding.ActivityReportDetailBinding;
import com.tiha.admin.anphat.ui.base.BaseTestActivity;

public class ActivityDetailReport extends BaseTestActivity<ActivityReportDetailBinding> {
    ActivityReportDetailBinding bd;
    @Override
    public ActivityReportDetailBinding getViewBinding() {
        return bd= ActivityReportDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        bd.appBarToolbar.imageBack.setOnClickListener(v -> finish());
        bd.appBarToolbar.textTitle.setText(getResources().getString(R.string.title_report_main));
        bd.layout1.tvTenBaoCao.setText(getResources().getString(R.string.title_report_main));
        bd.layout2.tvTenBaoCao.setText(getResources().getString(R.string.title_report_main));
        bd.layout3.tvTenBaoCao.setText(getResources().getString(R.string.title_report_main));
        bd.layout1.tvTenBaoCao.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityDetailReport.this, SpendActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        bd.layout2.tvTenBaoCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(this,);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
            }
        });
        bd.layout3.tvTenBaoCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(this,);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
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
