package com.anphat.supplier.ui.pay.history;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.data.entities.order.ChiTietDonInfo;
import com.anphat.supplier.databinding.ActivityHistoryBookingBinding;
import com.anphat.supplier.ui.base.BaseMVVMActivity;
import com.anphat.supplier.ui.booking.ProductAfterAdapter;
import com.anphat.supplier.utils.AppUtils;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.viewmodel.HistoryViewModel;

import java.util.List;

public class HistoryDetailActivity extends BaseMVVMActivity<ActivityHistoryBookingBinding, HistoryViewModel> {
    NewCustomer info;
    List<CartInfo> list = null;
    AppPreference preference;
    Double gg = 0.0;
    Double priceTotal = 0.0;
    private static final int CHANGEADDRESS = 1;
    String ID = "";
    ProductAfterAdapter adapter;

    @Override
    protected Class<HistoryViewModel> getClassVM() {
        return HistoryViewModel.class;
    }

    @Override
    public ActivityHistoryBookingBinding getViewBinding() {
        return ActivityHistoryBookingBinding.inflate(getLayoutInflater());
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        super.initView();
        showProgressDialog(true);
        preference = new AppPreference(this);
        info = PublicVariables.UserInfo;
        adapter = new ProductAfterAdapter(this);
        binding.rcl.setAdapter(adapter);
        binding.textAddress.setText(info.getHoTen() + " \n" + info.getSoDienThoai() + "    \n" +
                info.getDiaChi());

        setHeader();
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        ID = bundle.getString("ID");
        if (ID == null) return;
        viewModel.GetDetailHistory(ID);
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.ItemDetail.observe(this, result -> {
            if (result != null) {
                adapter.clear();
                adapter.addAll(result.getListChiTietDonHang());
                if (result.getListChiTietDonHang() != null) {
                    for (ChiTietDonInfo item : result.getListChiTietDonHang()) {
                        gg += item.Thanh_Tien;
                    }
                }
                setPrice(gg);
                binding.textEmployee.setText(result.TenNguoiGiao == null ? "" : result.TenNguoiGiao);
                binding.textAddress.setText(result.getDiachigiaohang());
            }
            showProgressDialog(false);
        });
    }

    private void setHeader() {
        binding.layoutHeader.imageBack.setOnClickListener(v -> finish());
        binding.layoutHeader.textTitle.setText(R.string.title_history_booking);
    }

    public void setPrice(Double price) {
        binding.textMoney.setText(String.format("%s%s", AppUtils.formatNumber("NO").format(price), getString(R.string.vnd)));
    }

    @Override
    public void onClick(View view) {

    }

}
