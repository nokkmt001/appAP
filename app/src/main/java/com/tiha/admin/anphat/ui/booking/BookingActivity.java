package com.tiha.admin.anphat.ui.booking;

import android.os.Bundle;
import android.view.View;

import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.data.entities.order.BookingInfo;
import com.tiha.admin.anphat.databinding.ActivityBookingBinding;
import com.tiha.admin.anphat.ui.base.BaseActivity;

public class BookingActivity extends BaseActivity implements BookingContract.View {
    BookingPresenter presenter;
    ActivityBookingBinding binding;
    ProductAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_booking;
    }

    @Override
    protected void initView() {
        binding = ActivityBookingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        adapter = new ProductAdapter(this);

        binding.rcl.setAdapter(adapter);
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
        if (info!=null){
            adapter.clear();
            adapter.addAll(info.getListChiTietDonHang());
        }
    }

    @Override
    public void GetBookingError(String error) {
        showMessage(error);
    }
}
