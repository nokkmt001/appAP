package com.tiha.anphat.ui.report.money.spend;

import android.view.View;

import com.tiha.anphat.data.entities.thongke.ThongkeChiInfo;
import com.tiha.anphat.databinding.ActivitySpendBinding;
import com.tiha.anphat.ui.base.BaseTestActivity;

import java.util.List;

public class SpendActivity extends BaseTestActivity<ActivitySpendBinding> implements SpendContract.View{
    ActivitySpendBinding bd;
    SpendPresenter presenter;
    SpendAdapter adapter;


    @Override
    public ActivitySpendBinding getViewBinding() {
        return bd = ActivitySpendBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        showProgressDialog(true);
        adapter = new SpendAdapter();
        bd.rvListData.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        presenter = new SpendPresenter(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSuccess(List<ThongkeChiInfo> list) {

    }

    @Override
    public void onError(String error) {

    }
}