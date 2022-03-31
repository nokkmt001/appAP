package com.anphat.supplier.ui.booking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.data.entities.order.CallInfo;
import com.anphat.supplier.data.entities.order.OrderInfo;
import com.anphat.supplier.databinding.ActivityBookingBinding;
import com.anphat.supplier.ui.address.ChangeAddressActivity;
import com.anphat.supplier.ui.base.BaseTestActivity;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.TestConstants;

import java.util.List;
import java.util.PrimitiveIterator;

@SuppressLint("SetTextI18n")
public class BookingActivity extends BaseTestActivity<ActivityBookingBinding> implements BookingContract.View {
    BookingPresenter presenter;
    ProductAdapter adapter;
    NewCustomer info;
    List<CartInfo> list = null;
    AppPreference preference;
    String gg = "";
    private static final int  CHANGEADDRESS= 1;

    @Override
    public ActivityBookingBinding getViewBinding() {
        return ActivityBookingBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        showProgressDialog(true);
        preference = new AppPreference(this);
        info = PublicVariables.UserInfo;
        adapter = new ProductAdapter(this);
        binding.rcl.setAdapter(adapter);
        binding.textAddress.setText(info.getHoTen() + " \n" + info.getSoDienThoai() + "    \n" +
                info.getDiaChi());
        binding.buttonOk.setOnClickListener(v -> alertDialog("", "Bạn chắc chắn muốn đặt hàng!", "Ok", null, (dialogInterface, i) -> {
            if (PublicVariables.itemBooking == null) {
                showProgressDialog(true);
                presenter.InsertOrder(list);
            } else {
                showMessage(getString(R.string.error_dont_booking));
            }
        }));

        binding.textAddress.setOnClickListener(v -> {
            Intent intent = new Intent(BookingActivity.this, ChangeAddressActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent,CHANGEADDRESS);
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
        if (bundle == null) return;
        gg = bundle.getString("ID");
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onInsertOrderSuccess(OrderInfo item, CallInfo info) {
        showProgressDialog(false);
        showToast("Đặt hàng thành công");
        Intent intentG = new Intent();
        intentG.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
        intentG.putExtra("eventName", "booking");
        sendBroadcast(intentG);
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

    @Override
    public void onCheckBookingSuccess(BookingInfo info) {
        PublicVariables.itemBooking = info;
    }

    @Override
    public void onCheckBookingError(String error) {
        PublicVariables.itemBooking = null;
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CHANGEADDRESS) {
                initView();
                initData();
            }
        }
    }
}
