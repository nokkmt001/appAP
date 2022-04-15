package com.anphat.supplier.ui.product.full;

import static androidx.core.text.HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;

import com.anphat.supplier.data.entities.BannerInfo;
import com.anphat.supplier.data.entities.gift.GiftInfo;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.databinding.ActivityChooseProductBinding;
import com.anphat.supplier.ui.base.BaseMainFragment;
import com.anphat.supplier.ui.cart.CartActivity;
import com.anphat.supplier.ui.product.detail.DetailAdapter;
import com.bumptech.glide.Glide;
import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.ui.booking.BookingActivity;
import com.anphat.supplier.ui.home.CommonFM;
import com.anphat.supplier.ui.home.HomeContract;
import com.anphat.supplier.ui.home.HomePresenter;
import com.anphat.supplier.utils.AppUtils;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.TestConstants;
import com.anphat.supplier.utils.adapterimage.ViewImageActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChooseProductFragment extends BaseMainFragment<ActivityChooseProductBinding> implements HomeContract.View, DetailPrContract.View {
    HomePresenter presenterProduct;
    Integer ID;
    DetailProductPresenter presenterOne;
    ProductNew info;
    Boolean isBuyNow = false;
    DetailAdapter adapter;
    GiftAdapter adapterGift;
    public Boolean isHome = false;
    GiftInfo itemMain = null;

    Boolean isClick = false;
    public ChooseProductFragment(Integer ID, boolean isFromHome) {
        this.ID = ID;
        this.isHome = isFromHome;
    }

    @Override
    protected void initView() {
        binding.checkboxNoGift.setChecked(true);
        adapter = new DetailAdapter(getActivity(), new ArrayList<>(), "");
        binding.rclMain.setAdapter(adapter);
        binding.rclMain.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapterGift = new GiftAdapter(getContext());
        binding.rclGift.setAdapter(adapterGift);

        adapter.setAll(true);
        binding.imageView.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ViewImageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle bundle = new Bundle();
            bundle.putString("PATHIMAGE", info.photo);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        binding.layoutHeader.imageBack.setOnClickListener(v -> {
            if (isHome) {
                Intent intentB = new Intent();
                intentB.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
                intentB.putExtra("eventName", "kk");
                getContext().sendBroadcast(intentB);
            }

            StartFragmentHome(isHome);
        });

        adapter.setClickListener((view1, position) -> {
            info = adapter.getItem(position);
            if (info == null) return;
            binding.layoutHeader.textTitle.setText(info.title);
            setView(info.title, info.price, info.description);
            binding.nestedScrollView.scrollTo(0, 0);
        });

        binding.layoutHeader.layoutCart.layoutClickNo.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), CartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            getContext().startActivity(intent);
        });

        adapterGift.setOnItemClickListener((giftInfo, view, position) -> {
            itemMain = giftInfo;
            isClick = false;
            binding.checkboxNoGift.setChecked(false);
        });

        binding.checkboxNoGift.setOnCheckedChangeListener((compoundButton, check) -> {
            compoundButton.setChecked(check);
            if (isClick){
                binding.checkboxNoGift.setChecked(true);
            }
            isClick = true;
            adapterGift.undoCheck();
        });

    }

    private void showFooter(boolean isShow, boolean footer) {
        binding.layoutOne.setVisibility(isShow ? View.VISIBLE : View.GONE);
        binding.textDetail.setVisibility(isShow ? View.VISIBLE : View.GONE);
        binding.layoutTwo.setVisibility(footer ? View.VISIBLE : View.GONE);
    }

    @Override
    public ActivityChooseProductBinding getViewBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return ActivityChooseProductBinding.inflate(inflater, container,false);
    }

    @Override
    protected void initData() {
        presenterOne = new DetailProductPresenter(this);
        presenterProduct = new HomePresenter(this);
        presenterOne.checkBooking();
        presenterOne.GetProduct(ID.toString());

        if (PublicVariables.listBooking!=null){
            binding.layoutHeader.layoutCart.textNumberCart.setText(String.valueOf(PublicVariables.listBooking.size()));
        } else {
            binding.layoutHeader.layoutCart.textNumberCart.setVisibility(View.GONE);
        }
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
    public void onGetListBannerSuccess(List<BannerInfo> list) {

    }

    @Override
    public void onGetListBannerError(String error) {

    }

    @Override
    public void onGetListProductPromotionSuccess(List<ProductNew> list) {

    }

    @Override
    public void onGetListProductPromotionError(String error) {

    }

    @Override
    public void onGetProductSuccess(ProductNew info) {
        this.info = info;
        binding.layoutHeader.textTitle.setText(info.title);
        setView(info.title, info.price, info.description);
        if (info.getProducts() != null) {
            adapter.clear();
            adapter.addAll(info.getProducts());
            PublicVariables.listKM = info.getProducts();
            showFooter(true, true);
        } else {
            showFooter(true, false);
        }
        if (info.getGifts()!=null){
            adapterGift.clear();
            adapterGift.addAll(info.getGifts());
        }
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

    Integer count = 1;
    String gg = "";

    @SuppressLint("NewApi")
    public void setView(String title, Double price, String description) {
        binding.textCountBuy.setText("1");
        binding.textName.setText(title);
        binding.textPrice.setText(AppUtils.formatNumber("NO").format(price));
        if (description != null) {
            binding.textDeception.setText(Html.fromHtml(description));
        } else {
            binding.textDeception.setVisibility(View.GONE);
        }

        if (info.content != null) {
            gg = info.content.replace("<li>\r\n\t<p>", "<li>").replace("</p>\r\n\t</li>", "</li>");
            binding.textContent.setText(Html.fromHtml(gg, FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE));

        } else {
            binding.textContent.setVisibility(View.GONE);
        }

        Log.d("CONTENTMAIN", info.content + "\n\n\n\n" + gg);
        count = 1;
        Log.d("CONTENT", gg);
        final Date date = new Date(System.currentTimeMillis());
        binding.imageAdd.setOnClickListener(view12 -> {
            count = count + 1;
            binding.textCountBuy.setText(count.toString());
        });
        binding.imageMinus.setOnClickListener(view13 -> {
            if (count != 1) {
                count = count - 1;
            }
            binding.textCountBuy.setText(count.toString());
        });
        binding.btnAddCart.setVisibility(View.VISIBLE);
        binding.btnBuyNow.setVisibility(View.VISIBLE);
        binding.btnAddCart.setOnClickListener(view1 -> {
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
        binding.btnBuyNow.setOnClickListener(v -> {
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
                .error(R.drawable.img_no_image)
                .into(binding.imageView);
    }

    private void StartFragmentHome(boolean isHome) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .hide(this)
                .show(isHome ? CommonFM.fragment : CommonFM.fragmentThree)
                .addToBackStack(null)
                .commit();
    }

}
