package com.tiha.admin.anphat.ui.cart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tiha.admin.anphat.ui.booking.BookingActivity;
import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.data.AppPreference;
import com.tiha.admin.anphat.data.entities.CartInfo;
import com.tiha.admin.anphat.data.entities.condition.CartCondition;
import com.tiha.admin.anphat.data.entities.order.CallInfo;
import com.tiha.admin.anphat.data.entities.order.OrderInfo;
import com.tiha.admin.anphat.databinding.ActivityCartBinding;
import com.tiha.admin.anphat.ui.base.BaseActivity;
import com.tiha.admin.anphat.ui.base.BaseEventClick;
import com.tiha.admin.anphat.utils.AppUtils;
import com.tiha.admin.anphat.utils.PublicVariables;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartActivity extends BaseActivity implements CartContract.View {
    ActivityCartBinding binding;
    CartAdapter adapter;
    CartPresenter presenter;
    Double priceTotal = 0.0;
    List<CartInfo> listAllData = new ArrayList<>();
    AppPreference preference;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cart;
    }

    @Override
    protected void initView() {
        preference = new AppPreference(this);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        onLoadDataHeader();
        adapter = new CartAdapter(CartActivity.this, new ArrayList<CartInfo>());
        binding.rylCart.setLayoutManager(new LinearLayoutManager(this));
        binding.rylCart.setAdapter(adapter);
        onAdapterClick();
        showNoResult(false);
        binding.buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog("", "Bạn chắc chắn muốn đặt hàng!", "Ok", null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (listAllData.size() != 0) {
                            presenter.InsertOrder(listAllData);
                        }
                    }
                });
            }
        });
    }

    private void onLoadDataHeader(){
        binding.textTitle.setText(getString(R.string.cart));
        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        binding.imageOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!TextUtils.isEmpty(preference.getBooking())) {
                    Intent intent = new Intent(CartActivity.this, BookingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("SOCT","CTY211101001");
                    startActivity(intent);
//                }
            }
        });
    }

    public void showNoResult(final boolean isShow) {
        binding.layoutMain.setVisibility(isShow ? View.GONE : View.VISIBLE);
        binding.textNoItemCart.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void initData() {

        presenter = new CartPresenter(this);
        presenter.GetListCart(PublicVariables.UserInfo.getNguoiDungMobileID());
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onGetListAllCartSuccess(List<CartInfo> list) {
        priceTotal = 0.0;
        listAllData = list;
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
        if (list.size() == 0) {
            showNoResult(true);
        }
    }

    @Override
    public void onGetListAllCartError(String error) {
        showMessage(error);
    }

    @Override
    public void onUpdateCartSuccess(CartCondition info) {
        initData();
    }

    @Override
    public void onUpdateCartError(String error) {
        showMessage(error);
    }

    @Override
    public void onDeleteCartSuccess() {
        initData();
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
    public void onGetProductInventorySuccess(Double result) { // ton kho

    }

    @Override
    public void onGetProductInventoryError(String error) {
        showMessage(error);
    }

    @Override
    public void onInsertOrderSuccess(OrderInfo item, CallInfo info) {
        preference.setBooking(item.getSoCt());
    }

    @Override
    public void onInsertOrderError(String error) {
        showMessage(error);
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
