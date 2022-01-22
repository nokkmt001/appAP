package com.tiha.anphat.ui.product.full;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tiha.anphat.R;
import com.tiha.anphat.data.AppPreference;
import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.condition.CartCondition;
import com.tiha.anphat.data.entities.condition.ProductCondition;
import com.tiha.anphat.databinding.ActivityChooseProductBinding;
import com.tiha.anphat.ui.base.BaseTestActivity;
import com.tiha.anphat.ui.booking.BookingActivity;
import com.tiha.anphat.ui.home.HomeContract;
import com.tiha.anphat.ui.home.HomePresenter;
import com.tiha.anphat.ui.product.ProductContract;
import com.tiha.anphat.ui.product.ProductPresenter;
import com.tiha.anphat.ui.product.detail.DetailAdapter;
import com.tiha.anphat.utils.AppUtils;
import com.tiha.anphat.utils.PublicVariables;
import com.tiha.anphat.utils.TestConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChooseProductActivity extends BaseTestActivity<ActivityChooseProductBinding> implements ProductContract.View, HomeContract.View {
    ActivityChooseProductBinding bd;
    ProductPresenter presenter;
    AppPreference preference;
    Boolean isBuyNow = false;
    ProductInfo info;
    DetailAdapter adapter;
    ProductCondition condition = new ProductCondition();
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private static final int PAGE_START = 1;
    private static int PAGE_RECORD = 20;
    private int currentPage = PAGE_START;
    HomePresenter presenterProduct;

    @Override
    public ActivityChooseProductBinding getViewBinding() {
        return bd = ActivityChooseProductBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        adapter = new DetailAdapter(this,new ArrayList<>(),"");
        bd.rclMain.setAdapter(adapter);
        bd.rclMain.setLayoutManager(new GridLayoutManager(this, 2));
        bd.rclMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isLoading = true;
                currentPage += 1;
                loadNextPage();
            }
        });

        bd.layoutHeader.textTitle.setText("Chi tiết sản phẩm");
        bd.layoutHeader.imageBack.setOnClickListener(view -> finish());

        adapter.setClickListener((view, position) -> {
            info = adapter.getItem(position);
            setView(info.getProduct_Name(), AppUtils.formatNumber("N0").format(info.getGiaBanLe()),
                    0.0, info.getDescription());
        });

    }

    Integer count = 1;

    private void setView(String title, String price, final Double number, String description) {
        bd.textCountBuy.setText(count.toString());
        bd.textName.setText(title);
        bd.textPrice.setText(price);
        bd.textDeception.setText(description);
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
            condition.setProductID(info.getProduct_ID());
            condition.setGhiChu("");
            condition.setCreateDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
            condition.setModifiedDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
            presenter.InsertCart(condition);
        });
        bd.btnBuyNow.setOnClickListener(v -> {
            if (preference.getBooking() != null && preference.getBooking().length() > 0) {
                showMessage(getString(R.string.error_dont_booking));
                return;
            }
            isBuyNow = true;
            CartCondition condition = new CartCondition();
            condition.setNguoiDungMobileID(PublicVariables.UserInfo.getNguoiDungMobileID());
            condition.setSoLuong(count);
            condition.setProductID(info.getProduct_ID());
            condition.setGhiChu("");
            condition.setCreateDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
            condition.setModifiedDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
            presenter.InsertCart(condition);
        });
        String url = "https://i.ibb.co/ZTVvwRc/gas-test.png";

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                bd.imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
        Picasso.with(this).load(url).into(target);
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        info = (ProductInfo) bundle.getSerializable("ITEM");
        if (info != null) {
            setView(info.getProduct_Name(), AppUtils.formatNumber("N0").format(info.getGiaBanLe()),
                    0.0, info.getDescription());
        }

        preference = new AppPreference(this);
        presenter = new ProductPresenter(this);
        presenterProduct = new HomePresenter(this);
        condition.setBegin(PAGE_START);
        condition.setUserName("TIHA");
        condition.setNhomLoaiHang(info.getCategory_ID());
        condition.setTextSearch("");
        condition.setEnd(PAGE_RECORD);

        presenterProduct.GetListProduct(condition);

    }

    public void loadNextPage() {
        condition.setBegin(condition.getEnd() + 1);
        condition.setEnd(condition.getEnd() + PAGE_RECORD);
        presenterProduct.GetListProduct(condition);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onGetListProductSuccess(List<ProductInfo> list, Integer total) {
        if (condition.getBegin() == 1) {
            if (list.size() == 0) {
//                showNoResult();
            }
            adapter.clear();
        }
        adapter.addAll(list);
    }

    @Override
    public void onGetListProductError(String error) {
        showMessage(error);
    }

    @Override
    public void onGetImageByProDuctIDSuccess(String imageBitmap) {

    }

    @Override
    public void onGetImageByProDuctIDError(String error) {

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
    public void onGetProductInventorySuccess(Double result) {

    }

    @Override
    public void onGetProductInventoryError(String error) {

    }

    @Override
    public void onGetListProductInventorySuccess(List<ProductInfo> list) {

    }

    @Override
    public void onGetListProductInventoryError(String error) {

    }
}
