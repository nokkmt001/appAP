package com.tiha.anphatsu.ui.product.full;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.tiha.anphatsu.R;
import com.tiha.anphatsu.data.AppPreference;
import com.tiha.anphatsu.data.entities.ProductNew;
import com.tiha.anphatsu.data.entities.condition.CartCondition;
import com.tiha.anphatsu.ui.base.BaseFragment;
import com.tiha.anphatsu.ui.booking.BookingActivity;
import com.tiha.anphatsu.ui.home.CommonFM;
import com.tiha.anphatsu.ui.home.HomeContract;
import com.tiha.anphatsu.ui.home.HomeFragment;
import com.tiha.anphatsu.ui.home.HomePresenter;
import com.tiha.anphatsu.utils.AppUtils;
import com.tiha.anphatsu.utils.PublicVariables;
import com.tiha.anphatsu.utils.TestConstants;
import com.tiha.anphatsu.utils.adapterimage.ViewImageActivity;

import java.util.Date;
import java.util.List;

public class ChooseProductFragment extends BaseFragment implements HomeContract.View, DetailPrContract.View {
    HomePresenter presenterProduct;
    Integer ID;
    DetailProductPresenter presenterOne;
    ProductNew info;

    TextView textTitle, textCountBuy, textName, textPrice, textDeception, textContent;
    ImageView imageBack, imageView, imageAdd, imageMinus;
    Button btnAddCart, btnBuyNow;
    AppPreference preference;
    Boolean isBuyNow = false;

    public ChooseProductFragment(Integer ID) {
        this.ID = ID;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_choose_product;
    }

    @Override
    protected void initView(View view) {
        textTitle = bind(view, R.id.textTitle);
        imageBack = bind(view, R.id.imageBack);
        imageView = bind(view, R.id.imageView);

        imageAdd = bind(view, R.id.imageAdd);
        imageMinus = bind(view, R.id.imageMinus);
        btnAddCart = bind(view, R.id.btnAddCart);
        btnBuyNow = bind(view, R.id.btnBuyNow);

        textCountBuy = bind(view, R.id.textCountBuy);
        textName = bind(view, R.id.textName);
        textPrice = bind(view, R.id.textPrice);
        textDeception = bind(view, R.id.textDeception);
        textContent = bind(view, R.id.textContent);

        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ViewImageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle bundle = new Bundle();
            bundle.putString("PATHIMAGE", info.photo);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        textTitle.setText("Chi tiết sản phẩm");
        imageBack.setOnClickListener(v -> {
            Intent intentB = new Intent();
            intentB.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
            intentB.putExtra("eventShow", "kk");
            getContext().sendBroadcast(intentB);

            StartFragmentHome();
        });

//      Fragment me=this;
//      imageBack.setOnClickListener(v -> getActivity().getFragmentManager().beginTransaction().remove().commit());
    }

    @Override
    protected void initData() {
        presenterOne = new DetailProductPresenter(this);
        presenterProduct = new HomePresenter(this);

        presenterOne.GetProduct(ID.toString());
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetListProductSuccess(List<ProductNew> list) {

    }

    @Override
    public void onGetListProductError(String error) {

    }

    @Override
    public void onInsertCartSuccess(CartCondition info) {
        Toast.makeText(getContext(), R.string.add_cart_success, Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        intent.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
        intent.putExtra("eventName", TestConstants.RECEIVE_ThayDoiGioHang);
        intent.putExtra("ItemGioHang", info);
        getActivity().sendBroadcast(intent);
        if (isBuyNow) {
            Intent intent1 = new Intent(getContext(), BookingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("ID", info.getID().toString());
            intent1.putExtras(bundle);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
        }
    }

    @Override
    public void onInsertCartError(String error) {

    }

    @Override
    public void onGetProductSuccess(ProductNew info) {
        this.info = info;
        setView(info.title, info.price, info.description);
    }

    @Override
    public void onGetProductError(String error) {

    }

    Integer count = 1;

    private void setView(String title, Double price, String description) {
        textCountBuy.setText("1");
        textName.setText(title);
        textPrice.setText(AppUtils.formatNumber("NO").format(price));
        textDeception.setText(Html.fromHtml(description));
        textContent.setText(Html.fromHtml(info.content));
        count = 1;
        final Date date = new Date(System.currentTimeMillis());
        imageAdd.setOnClickListener(view12 -> {
            count = count + 1;
            textCountBuy.setText(count.toString());
        });
        imageMinus.setOnClickListener(view13 -> {
            if (count != 1) {
                count = count - 1;
            }
            textCountBuy.setText(count.toString());
        });
        btnAddCart.setVisibility(View.VISIBLE);
        btnBuyNow.setVisibility(View.VISIBLE);
        btnAddCart.setOnClickListener(view1 -> {
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
        btnBuyNow.setOnClickListener(v -> {
            if (preference.getBooking() != null && preference.getBooking().length() > 0) {
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
                .error(R.drawable.img_no_image)
                .into(imageView);

    }

    private void StartFragmentHome() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .hide(this)
                .show(CommonFM.fragment)
                .addToBackStack(null)
                .commit();
    }

}
