package com.tiha.anphatsu.ui.booking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tiha.anphatsu.R;
import com.tiha.anphatsu.data.AppPreference;
import com.tiha.anphatsu.data.entities.CartInfo;
import com.tiha.anphatsu.data.entities.NewCustomer;
import com.tiha.anphatsu.data.entities.order.CallInfo;
import com.tiha.anphatsu.data.entities.order.OrderInfo;
import com.tiha.anphatsu.databinding.ActivityBookingBinding;
import com.tiha.anphatsu.ui.base.BaseTestActivity;
import com.tiha.anphatsu.utils.PublicVariables;

import java.util.List;

@SuppressLint("SetTextI18n")
public class BookingActivity extends BaseTestActivity<ActivityBookingBinding> implements BookingContract.View {
    ActivityBookingBinding binding;
    BookingPresenter presenter;
    ProductAdapter adapter;
    NewCustomer info;
    List<CartInfo> list = null;
    AppPreference preference;
    String gg = "";

    @Override
    public ActivityBookingBinding getViewBinding() {
        return binding = ActivityBookingBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        showProgressDialog(true);
        preference = new AppPreference(this);
        info = PublicVariables.UserInfo;
        adapter = new ProductAdapter(this);
        binding.rcl.setAdapter(adapter);
        binding.textAddress.setText("Địa chỉ nhận hàng \n" + info.getHoTen() + " | " + info.getSoDienThoai() + "\n" +
                info.getDiaChi());
        binding.buttonOk.setOnClickListener(v -> {
            if (preference.getBooking() == null) {
                showProgressDialog(true);
                presenter.InsertOrder(list);
            } else {
                showMessage(getString(R.string.error_dont_booking));
            }
        });
        setHeader();

    }

    private void setHeader() {
        binding.layoutHeader.imageBack.setOnClickListener(v -> finish());
        binding.layoutHeader.textTitle.setText("Đặt hàng");
    }

    @Override
    protected void initData() {
        presenter = new BookingPresenter(this);
        presenter.GetListAllCart(PublicVariables.UserInfo.getNguoiDungMobileID());
        Bundle bundle = getIntent().getExtras();
        gg = bundle.getString("ID");
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onInsertOrderSuccess(OrderInfo item, CallInfo info) {
        showProgressDialog(false);
        showToast("Đặt hàng thành công");
        preference.setBooking(item.getSoCt());
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onInsertOrderError(String error) {
        showProgressDialog(false);
        showMessage(error);
    }

    @Override
    public void onGetListAllCartSuccess(List<CartInfo> list) {
        PublicVariables.listBooking = list;
        this.list = list;
        showProgressDialog(false);
        adapter.clear();
        adapter.addAll(list);
    }

    @Override
    public void onGetListAllCartError(String error) {
        showMessage(error);
        showProgressDialog(false);
    }

}
