package com.anphat.supplier.ui.booking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.databinding.ActivityBookingBinding;
import com.anphat.supplier.ui.address.ChangeAddressActivity;
import com.anphat.supplier.ui.base.BaseMVVMActivity;
import com.anphat.supplier.viewmodel.BookingViewModel;
import com.anphat.supplier.utils.AppUtils;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.TestConstants;

import java.util.List;

@SuppressLint("SetTextI18n")
public class BookingActivity extends BaseMVVMActivity<ActivityBookingBinding, BookingViewModel> {
    ProductAdapter adapter;
    NewCustomer info;
    List<CartInfo> list = null;
    AppPreference preference;
    String gg = "";
    Double priceTotal = 0.0;
    private static final int CHANGEADDRESS = 1;

    @Override
    protected Class<BookingViewModel> getClassVM() {
        return BookingViewModel.class;
    }

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
            showProgressDialog(true);
            viewModel.InsertOrder(list);

        }));

        binding.textAddress.setOnClickListener(v -> {
            Intent intent = new Intent(BookingActivity.this, ChangeAddressActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, CHANGEADDRESS);
        });

        setHeader();
    }

    private void setHeader() {
        binding.layoutHeader.imageBack.setOnClickListener(v -> finish());
        binding.layoutHeader.textTitle.setText("Đặt hàng");
    }

    @Override
    protected void initData() {
        viewModel.GetListAllCart();
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        gg = bundle.getString("ID");
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.itemBooking.observe(this, orderInfo -> {
            if (orderInfo != null) {
                showToast("Đặt hàng thành công");
                Intent intentG = new Intent();
                intentG.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
                intentG.putExtra("eventName", "booking");
                sendBroadcast(intentG);
                finish();
            }
            showProgressDialog(false);
        });

        viewModel.itemListCart.observe(this, cartInfos -> {
            PublicVariables.listBooking = cartInfos;
            this.list = cartInfos;
            showProgressDialog(false);
            adapter.clear();
            adapter.addAll(cartInfos);
            if (adapter.getListAllData() != null) {
                for (CartInfo item : cartInfos) {
                    if (item.getDonGia() != null) {
                        priceTotal = priceTotal + Double.parseDouble(String.valueOf(item.getDonGia() * item.getSoLuong()));
                    }
                }
            }
            setPrice(priceTotal);
        });

        viewModel.mItemCheckBooking.observe(this, bookingInfo -> {
            PublicVariables.itemBooking = bookingInfo;
        });
    }

    @Override
    public void onClick(View view) {
    }

    public void setPrice(Double price) {
        binding.textMoney.setText(String.format("%s%s", AppUtils.formatNumber("NO").format(price), getString(R.string.vnd)));
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
