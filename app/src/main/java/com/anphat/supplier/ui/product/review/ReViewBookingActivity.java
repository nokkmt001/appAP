package com.anphat.supplier.ui.product.review;

import android.os.Bundle;
import android.view.View;

import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.databinding.ActivityReviewBookingBinding;
import com.anphat.supplier.ui.base.BaseTestActivity;
import com.anphat.supplier.utils.PublicVariables;

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
