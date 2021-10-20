package com.tiha.anphat.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.CartInfo;
import com.tiha.anphat.data.entities.condition.CartCondition;
import com.tiha.anphat.databinding.ActivityCartBinding;
import com.tiha.anphat.ui.base.BaseActivity;
import com.tiha.anphat.ui.base.BaseEventClick;
import com.tiha.anphat.utils.AppUtils;
import com.tiha.anphat.utils.PublicVariables;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartActivity extends BaseActivity implements CartContract.View {
    ActivityCartBinding binding;
    CartAdapter adapter;
    CartPresenter presenter;
    Double priceTotal = 0.0;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_cart;
    }

    @Override
    protected void onInit() {
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.layoutHeader.textTitle.setText(getString(R.string.cart));
        binding.layoutHeader.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        adapter = new CartAdapter(CartActivity.this, new ArrayList<CartInfo>());
        binding.rylCart.setLayoutManager(new LinearLayoutManager(this));
        binding.rylCart.setAdapter(adapter);
        onAdapterClick();
        showNoResult(false);
    }

    public void showNoResult(final boolean isShow) {
        binding.layoutMain.setVisibility(isShow ? View.GONE : View.VISIBLE);
        binding.textNoItemCart.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onLoadData() {
        presenter = new CartPresenter(this);
        presenter.GetListCart(PublicVariables.UserInfo.getNguoiDungMobileID());
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onGetListAllCartSuccess(List<CartInfo> list) {
        priceTotal = 0.0;
        adapter.clear();
        adapter.addAll(list);
        if (list != null) {
            for (CartInfo item : list) {
                priceTotal = priceTotal + Double.parseDouble(String.valueOf(item.getDonGia() * item.getSoLuong()));
            }
            setPrice(priceTotal);
        } else {
            setPrice(0.0);
        }

        assert list != null;
        if (list.size()==0){
            showNoResult(true);
        }

    }

    @Override
    public void onGetListAllCartError(String error) {
        showMessage(error);
    }

    @Override
    public void onUpdateCartSuccess(CartCondition info) {
        onLoadData();
    }

    @Override
    public void onUpdateCartError(String error) {
        showMessage(error);
    }

    @Override
    public void onDeleteCartSuccess() {
        onLoadData();
    }

    @Override
    public void onDeleteCartError(String error) {
        showMessage(error);
    }

    @Override
    public void onGetDonGiaProductByUserSuccess(Double price) {
    }

    @Override
    public void onGetDonGiaProductByUserError(String error) {
        showMessage(error);
    }

    @Override
    public void onGetProductInventorySuccess(Integer result) { // ton kho

    }

    @Override
    public void onGetProductInventoryError(String error) {

    }

    public void onAdapterClick() {
        adapter.setClickListener(new BaseEventClick.OnClickListener() {
            @Override
            public void onClick(View view, int position) {
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
                            DeleteCart(info.getID());
                        } else {
                            adapter.add(info, position);
                            UpdateCart(info);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void UpdateCart(CartInfo info) {
        Date date = new Date(System.currentTimeMillis());
        CartCondition condition = new CartCondition();
        condition.setID(info.getID());
        condition.setProductID(info.getProductID());
        condition.setGhiChu(info.getGhiChu());
        condition.setNguoiDungMobileID(info.getNguoiDungMobileID());
        condition.setCreateDate(info.getCreateDate());
        condition.setSoLuong(info.getSoLuong());
        condition.setModifiedDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
        presenter = new CartPresenter(this);
        presenter.UpdateCart(condition);
    }

    public void DeleteCart(Integer ID) {
        presenter = new CartPresenter(this);
        presenter.DeleteCart(ID);
    }

    public void setPrice(Double price) {
        binding.textCountTem.setText(String.format("%s%s", AppUtils.formatNumber("NO").format(price), getString(R.string.vnd)));
        binding.textPriceFinal.setText(binding.textCountTem.getText().toString());
    }
}
