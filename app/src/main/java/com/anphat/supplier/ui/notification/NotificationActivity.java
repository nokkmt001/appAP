package com.anphat.supplier.ui.notification;

import android.view.View;

import com.anphat.supplier.R;
import com.anphat.supplier.databinding.ActivityNotiBinding;
import com.anphat.supplier.ui.base.BaseTestActivity;

public class NotificationActivity extends BaseTestActivity<ActivityNotiBinding> {
    @Override
    public ActivityNotiBinding getViewBinding() {
        return ActivityNotiBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        binding.textMainTitle.setText(R.string.title_text_notification);
        binding.imageBack.setOnClickListener(view -> finish());
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }

}