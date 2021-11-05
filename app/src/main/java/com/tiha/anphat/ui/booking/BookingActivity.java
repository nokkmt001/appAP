package com.tiha.anphat.ui.booking;

import android.os.Bundle;
import android.view.View;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.order.BookingInfo;
import com.tiha.anphat.ui.base.BaseActivity;

public class BookingActivity extends BaseActivity implements BookingContract.View {
    BookingPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_booking;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();

        assert bundle != null;
        String gg = bundle.getString("SOCT");
        presenter = new BookingPresenter(this);
        presenter.GetBooking("CTY211101001");
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void GetBookingSuccess(BookingInfo info) {

    }

    @Override
    public void GetBookingError(String error) {
        showMessage(error);
    }
}
