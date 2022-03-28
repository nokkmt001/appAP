package com.anphat.supplier.ui.product.full;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.anphat.supplier.data.entities.order.BookingInfo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.data.entities.condition.ProductCondition;
import com.anphat.supplier.databinding.ActivityChooseProductBinding;
import com.anphat.supplier.ui.base.BaseTestActivity;
import com.anphat.supplier.ui.booking.BookingActivity;
import com.anphat.supplier.ui.home.HomeContract;
import com.anphat.supplier.ui.home.HomePresenter;
import com.anphat.supplier.ui.product.detail.DetailAdapter;
import com.anphat.supplier.utils.AppUtils;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.TestConstants;
import com.anphat.supplier.utils.adapterimage.ViewImageActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChooseProductActivity extends BaseTestActivity<ActivityChooseProductBinding> implements HomeContract.View, DetailPrContract.View {
    ActivityChooseProductBinding bd;
    AppPreference preference;
    Boolean isBuyNow = false;
    ProductNew info;
    DetailAdapter adapter;
    ProductCondition condition = new ProductCondition();
    HomePresenter presenterProduct;
    Integer ID;
    DetailProductPresenter presenterOne;

    @Override
    public ActivityChooseProductBinding getViewBinding() {
        return bd = ActivityChooseProductBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        presenterOne = new DetailProductPresenter(this);
        adapter = new DetailAdapter(this,new ArrayList<>(),"");
        bd.rclMain.setAdapter(adapter);
        bd.rclMain.setLayoutManager(new GridLayoutManager(this, 2));

        bd.layoutHeader.textTitle.setText("Chi tiết sản phẩm");
        bd.layoutHeader.imageBack.setOnClickListener(view -> finish());

        adapter.setClickListener((view, position) -> {
        });
        bd.imageView.setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewImageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle bundle = new Bundle();
            bundle.putString("PATHIMAGE", info.photo);
            intent.putExtras(bundle);
            startActivity(intent);
        });

    }

    Integer count = 1;

    private void setView(String title, Double price, final Double number, String description) {
        bd.textCountBuy.setText(count.toString());
        bd.textName.setText(title);
        bd.textPrice.setText(AppUtils.formatNumber("NO").format(price));
        bd.textDeception.setText(Html.fromHtml(description));
        bd.textContent.setText(Html.fromHtml(info.content));
        count = 1;
        final Date date = new Date(System.currentTimeMillis());
        bd.imageAdd.setOnClickListener(view12 -> {
            count = count + 1;
            bd.textCountBuy.setText(count.toString());
        });
        bd.imageMinus.setOnClickListener(view13 -> {
            if (count != 1) {
                count = count - 1;
            }
            bd.textCountBuy.setText(count.toString());
        });
        bd.btnAddCart.setVisibility(View.VISIBLE);
        bd.btnBuyNow.setVisibility(View.VISIBLE);
        bd.btnAddCart.setOnClickListener(view1 -> {
            isBuyNow = false;
            CartCondition condition = new CartCondition();
            condition.setNguoiDungMobileID(PublicVariables.UserInfo.getNguoiDungMobileID());
            condition.setSoLuong(count);
            condition.setProductID(info.code);
            condition.setGhiChu("");
            condition.setCreateDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
            condition.setModifiedDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
            presenterProduct.InsertCart(condition);
        });
        bd.btnBuyNow.setOnClickListener(v -> {
            if (PublicVariables.itemBooking != null) {
                showMessage(getString(R.string.error_dont_booking));
                return;
            }
            isBuyNow = true;
            CartCondition condition = new CartCondition();
            condition.setNguoiDungMobileID(PublicVariables.UserInfo.getNguoiDungMobileID());
            condition.setSoLuong(count);
            condition.setProductID(info.code);
            condition.setGhiChu("");
            condition.setCreateDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
            condition.setModifiedDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
            presenterProduct.InsertCart(condition);
        });
        String url = "https://gasanphat.com/" + info.photo;

        Glide.with(this)
                .load(url)
                .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        bd.imageView.setImageBitmap(AppUtils.drawableToBitmap(resource));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        ID = bundle.getInt("ITEM");
        presenterOne.GetProduct(ID.toString());
        presenterOne.checkBooking();
        preference = new AppPreference(this);
        presenterProduct = new HomePresenter(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onGetListProductSuccess(List<ProductNew> list) {
//        if (condition.getBegin() == 1) {
//            if (list.size() == 0) {
////                showNoResult();
//            }
//            adapter.clear();
//        }
//        adapter.addAll(list);
    }

    @Override
    public void onGetListProductError(String error) {
        showMessage(error);
    }

    @Override
    public void onInsertCartSuccess(CartCondition info) {
        Toast.makeText(this, R.string.add_cart_success, Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        intent.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
        intent.putExtra("eventName", TestConstants.RECEIVE_ThayDoiGioHang);
        intent.putExtra("ItemGioHang", info);
        sendBroadcast(intent);
        if (isBuyNow) {
            Intent intent1 = new Intent(this, BookingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("ID", info.getID().toString());
            intent1.putExtras(bundle);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
        }
    }

    @Override
    public void onInsertCartError(String error) {
        showMessage(error);
    }

    @Override
    public void onGetProductSuccess(ProductNew info) {
        this.info = info;
        setView(info.title,info.price,1.0,info.description);
    }

    @Override
    public void onGetProductError(String error) {

    }

    @Override
    public void onCheckBookingSuccess(BookingInfo info) {
        PublicVariables.itemBooking = info;
    }

    @Override
    public void onCheckBookingError(String error) {
        PublicVariables.itemBooking = null;
    }

}
