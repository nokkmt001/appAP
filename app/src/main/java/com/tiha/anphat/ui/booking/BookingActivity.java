package com.tiha.anphat.ui.booking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tiha.anphat.R;
import com.tiha.anphat.data.AppPreference;
import com.tiha.anphat.data.entities.CartInfo;
import com.tiha.anphat.data.entities.NewCustomer;
import com.tiha.anphat.data.entities.order.BookingInfo;
import com.tiha.anphat.data.entities.order.CallInfo;
import com.tiha.anphat.data.entities.order.OrderInfo;
import com.tiha.anphat.databinding.ActivityBookingBinding;
import com.tiha.anphat.databinding.ActivityCartBinding;
import com.tiha.anphat.ui.base.BaseActivity;
import com.tiha.anphat.ui.base.BaseTestActivity;
import com.tiha.anphat.utils.PublicVariables;

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
        list = PublicVariables.listBooking;
        presenter = new BookingPresenter(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            adapter.clear();
            adapter.addAll(list);
            showProgressDialog(false);
        } else {
            gg = bundle.getString("ID");
            presenter.GetListAllCart(PublicVariables.UserInfo.getNguoiDungMobileID());
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onInsertOrderSuccess(OrderInfo item, CallInfo info) {
        showToast("Đặt hàng thành công");
        preference.setBooking(item.getSoCt());
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onInsertOrderError(String error) {
        showMessage(error);
    }

    @Override
    public void onGetListAllCartSuccess(List<CartInfo> list) {
        PublicVariables.listBooking = list;
        showProgressDialog(false);
        if (!gg.equals("")) {
            for (CartInfo info : list) {
                if (gg.equals(info.getID().toString())) {
                    adapter.clear();
                    adapter.addData(info);
                }
            }
        }
    }

    @Override
    public void onGetListAllCartError(String error) {
        showMessage(error);
        showProgressDialog(false);
    }
}
