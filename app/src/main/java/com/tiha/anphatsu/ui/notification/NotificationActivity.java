package com.tiha.anphatsu.ui.notification;

import android.view.View;

import com.tiha.anphatsu.R;
import com.tiha.anphatsu.databinding.ActivityNotiBinding;
import com.tiha.anphatsu.ui.base.BaseTestActivity;

public class NotificationActivity extends BaseTestActivity<ActivityNotiBinding> {

    @Override
    public ActivityNotiBinding getViewBinding() {
        return ActivityNotiBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        binding.textMainTitle.setText(R.string.title_text_notification);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}