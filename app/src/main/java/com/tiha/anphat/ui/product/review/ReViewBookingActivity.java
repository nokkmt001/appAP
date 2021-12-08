package com.tiha.anphat.ui.product.review;

import android.os.Bundle;
import android.view.View;

import com.tiha.anphat.data.entities.NewCustomer;
import com.tiha.anphat.data.entities.condition.CartCondition;
import com.tiha.anphat.databinding.ActivityReviewBookingBinding;
import com.tiha.anphat.ui.base.BaseTestActivity;
import com.tiha.anphat.utils.PublicVariables;

public class ReViewBookingActivity extends BaseTestActivity<ActivityReviewBookingBinding> {
    ActivityReviewBookingBinding bd;
    CartCondition info = null;
    NewCustomer user =null;

    @Override
    public ActivityReviewBookingBinding getViewBinding() {
        return bd = ActivityReviewBookingBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        user = PublicVariables.UserInfo;
        bd.textAddress.setText(user.getDiaChi());
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        info = (CartCondition) bundle.getSerializable("ItemCart");
        if (info==null) return;
    }

    @Override
    public void onClick(View v) {

    }
}
