package com.anphat.supplier.ui.notification;

import android.os.Bundle;
import android.view.View;

import com.anphat.supplier.data.entities.condition.NotificationMain;
import com.anphat.supplier.databinding.ActivityNotificationDetailBinding;
import com.anphat.supplier.ui.base.BaseMVVMActivity;
import com.anphat.supplier.viewmodel.BaseViewModel;

public class NotificationDetailActivity extends BaseMVVMActivity<ActivityNotificationDetailBinding, BaseViewModel> {

    NotificationMain item;

    @Override
    protected Class<BaseViewModel> getClassVM() {
        return BaseViewModel.class;
    }

    @Override
    protected void startInit(Bundle savedInstanceState) {
        super.startInit(savedInstanceState);
        item = null;
    }

    @Override
    public ActivityNotificationDetailBinding getViewBinding() {
        return ActivityNotificationDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void initView() {
        super.initView();
        binding.layoutHeader.imageBack.setOnClickListener(view -> finish());

    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        item = (NotificationMain) bundle.getSerializable("ITEM");
        binding.layoutHeader.textTitle.setText(item.TieuDe);

    }
}
