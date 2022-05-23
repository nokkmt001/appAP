package com.anphat.supplier.ui.cart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.databinding.ActivityCartBinding;
import com.anphat.supplier.ui.base.BaseMVVMActivity;
import com.anphat.supplier.ui.booking.BookingActivity;
import com.anphat.supplier.viewmodel.CartViewModel;
import com.anphat.supplier.utils.AppUtils;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.TestConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartActivity extends BaseMVVMActivity<ActivityCartBinding, CartViewModel> {
    CartAdapter adapter;
    Double priceTotal = 0.0;
    List<CartInfo> listAllData = new ArrayList<>();
    AppPreference preference;

    @Override
    protected Class<CartViewModel> getClassVM() {
        return CartViewModel.class;
    }

    @Override
    public ActivityCartBinding getViewBinding() {
        return ActivityCartBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        showProgressDialog(true);
        preference = new AppPreference(this);
        onLoadDataHeader();
        adapter = new CartAdapter(CartActivity.this, new ArrayList<>());
        binding.rylCart.setLayoutManager(new LinearLayoutManager(this));
        binding.rylCart.setAdapter(adapter);
        onAdapterClick();
        showNoResult(false);
        binding.buttonOK.setOnClickListener(view1 -> {
            PublicVariables.listBooking = adapter.getAllData();
            Intent intent = new Intent(CartActivity.this, BookingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            this.finish();
        });
    }

    private void onLoadDataHeader() {
        binding.textTitle.setText(getString(R.string.cart));
        binding.imageBack.setOnClickListener(view -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);

            Intent intent1 = new Intent();
            intent1.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
            intent1.putExtra("eventName", TestConstants.RECEIVE_ThayDoiGioHang);
            sendBroadcast(intent1);
            this.finish();
        });
        binding.imageOrder.setVisibility(View.GONE);
    }

    public void showNoResult(final boolean isShow) {
        binding.layoutMain.setVisibility(isShow ? View.GONE : View.VISIBLE);
        binding.textNoItemCart.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void initData() {
        viewModel.GetListAllCart();
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.itemListCart.observe(this, list -> {
            if (list.size() == 0) {
                showNoResult(true);
                showProgressDialog(false);
                return;
            }

            try {
                priceTotal = 0.0;
                listAllData = list;
                adapter.clear();
                adapter.addAll(list);
                for (CartInfo item : list) {
                    if (item.getDonGia() != null) {
                        priceTotal = priceTotal + Double.parseDouble(String.valueOf(item.getDonGia() * item.getSoLuong()));
                    }
                }
            } catch (Exception ignored) {

            }
            setPrice(priceTotal);
            showProgressDialog(false);
        });

        viewModel.mItemDeleteCart.observe(this, aBoolean -> {
            if (aBoolean){
                initData();

            }
        });

        viewModel.mItemUpdateCart.observe(this, condition -> initData());

    }

    @Override
    public void onClick(View view) {

    }


    @SuppressLint("NonConstantResourceId")
    public void onAdapterClick() {
        adapter.setClickListener((view, position) -> {
            CartInfo info = adapter.getItem(position);
            Integer count = info.getSoLuong();
            switch (view.getId()) {
                case R.id.imageAdd:
                    count = count + 1;
                    info.setSoLuong(count);
                    adapter.remoteItem(position);
                    adapter.add(info, position);
                    UpdateCart(info);
                    break;
                case R.id.imageMinus:
                    count = count - 1;
                    info.setSoLuong(count);
                    adapter.remoteItem(position);
                    if (count == 0) {
                        DeleteCart(info.getID().toString());
                    } else {
                        adapter.add(info, position);
                        UpdateCart(info);
                    }
                    break;
                default:
                    break;
            }
        });
    }

    public void UpdateCart(CartInfo info) {
        Date date = new Date(System.currentTimeMillis());
        CartCondition condition = new CartCondition();
        condition.setID(info.getID());
        condition.setProductID(info.getProductID());
        condition.SanPhamChinhID = info.SanPhamChinhID;
        condition.setGhiChu(info.getGhiChu());
        condition.setNguoiDungMobileID(info.getNguoiDungMobileID());
        condition.setCreateDate(info.getCreateDate());
        condition.setSoLuong(info.getSoLuong());
        condition.setModifiedDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
        viewModel.UpdateCart(condition);
    }

    public void DeleteCart(String ID) {
        viewModel.DeleteCart(ID);
    }

    public void setPrice(Double price) {
        binding.textCountTem.setText(String.format("%s%s", AppUtils.formatNumber("NO").format(price), getString(R.string.vnd)));
        binding.textPriceFinal.setText(binding.textCountTem.getText().toString());
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            int ACTION_ADD = 1;
            if (requestCode == ACTION_ADD) {
                showProgressDialog(true);
                initData();
            }
        }
    }
}
