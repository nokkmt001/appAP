package com.anphat.supplier.ui.notification;

import android.view.View;

import com.anphat.supplier.databinding.ActivityNewsNotificationBinding;
import com.anphat.supplier.ui.base.BaseMVVMActivity;
import com.anphat.supplier.viewmodel.BaseViewModel;

public class NewsNotificationActivity extends BaseMVVMActivity<ActivityNewsNotificationBinding, BaseViewModel> {
    @Override
    protected Class<BaseViewModel> getClassVM() {
        return BaseViewModel.class;
    }

    @Override
    public ActivityNewsNotificationBinding getViewBinding() {
        return null;
    }

    @Override
    public void onClick(View view) {

    }
}
