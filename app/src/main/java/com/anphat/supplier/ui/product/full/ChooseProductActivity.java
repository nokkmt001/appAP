package com.anphat.supplier.ui.product.full;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.anphat.supplier.data.entities.gift.GiftInfo;
import com.anphat.supplier.ui.base.BaseMVVMActivity;
import com.anphat.supplier.ui.cart.CartActivity;
import com.anphat.supplier.viewmodel.DetailViewModel;
import com.bumptech.glide.Glide;
import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.databinding.ActivityChooseProductBinding;
import com.anphat.supplier.ui.booking.BookingActivity;
import com.anphat.supplier.ui.product.detail.DetailAdapter;
import com.anphat.supplier.utils.AppUtils;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.TestConstants;
import com.anphat.supplier.utils.adapterimage.ViewImageActivity;

import java.util.ArrayList;
import java.util.Date;

public class ChooseProductActivity extends BaseMVVMActivity<ActivityChooseProductBinding, DetailViewModel> {
    Integer ID;
    ProductNew info;
    Boolean isBuyNow = false;
    DetailAdapter adapter;
    GiftAdapter adapterGift;
    public Boolean isHome = false;
    GiftInfo itemMain = null;
    Boolean isClick = false;

    @Override
    protected void initView() {
        binding.checkboxNoGift.setChecked(true);
        adapter = new DetailAdapter(this, new ArrayList<>(), "");
        binding.rclMain.setAdapter(adapter);
        binding.rclMain.setLayoutManager(new GridLayoutManager(this, 2));
        adapterGift = new GiftAdapter(this);
        binding.rclGift.setAdapter(adapterGift);

        adapter.setAll(true);
        binding.imageView.setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewImageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle bundle = new Bundle();
            bundle.putString("PATHIMAGE", info.photo);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        binding.layoutHeader.imageBack.setOnClickListener(v -> {
            finish();
        });

        adapter.setClickListener((view1, position) -> {
            info = adapter.getItem(position);
            if (info == null) return;
            binding.layoutHeader.textTitle.setText(info.title);
            setView(info.title, info.price, info.description);
            adapterGift.clear();
            adapterGift.addAll(info.gifts);
            binding.nestedScrollView.scrollTo(0, 0);
        });

        binding.layoutHeader.layoutCart.layoutClickNo.setOnClickListener(view -> {
            Intent intent = new Intent(this, CartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        adapterGift.setOnItemClickListener((giftInfo, view, position) -> {
            itemMain = giftInfo;
            isClick = false;
            binding.checkboxNoGift.setChecked(false);
        });

        binding.checkboxNoGift.setOnCheckedChangeListener((compoundButton, check) -> {
            compoundButton.setChecked(check);
//            if (isClick){
//                binding.checkboxNoGift.setChecked(true);
//            }
            isClick = check;
            adapterGift.undoCheck();
        });

    }

    private void showFooter(boolean isShow, boolean footer) {
        binding.layoutOne.setVisibility(isShow ? View.VISIBLE : View.GONE);
        binding.textDetail.setVisibility(isShow ? View.VISIBLE : View.GONE);
        binding.layoutTwo.setVisibility(footer ? View.VISIBLE : View.GONE);
    }

    @Override
    protected Class<DetailViewModel> getClassVM() {
        return DetailViewModel.class;
    }

    @Override
    public ActivityChooseProductBinding getViewBinding() {
        return ActivityChooseProductBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        ID = bundle.getInt("ITEM");
        viewModel.checkBooking();
        viewModel.GetProduct(ID.toString());

        if (PublicVariables.listBooking != null && PublicVariables.listBooking.size() > 0) {
            binding.layoutHeader.layoutCart.textNumberCart.setVisibility(View.VISIBLE);
            binding.layoutHeader.layoutCart.textNumberCart.setText(String.valueOf(PublicVariables.listBooking.size()));
        } else {
            binding.layoutHeader.layoutCart.textNumberCart.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.mItemInsertCart.observe(this, result -> {
            if (result != null) {
                Toast.makeText(this, R.string.add_cart_success, Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
                intent.putExtra("eventName", TestConstants.RECEIVE_ThayDoiGioHang);
                intent.putExtra("ItemGioHang", result.Data);
                sendBroadcast(intent);
                if (isBuyNow) {
                    Intent intent1 = new Intent(this, BookingActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", result.Data.getID().toString());
                    intent1.putExtras(bundle);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                }
            }
        });

        viewModel.mItemGetProduct.observe(this, productNew -> {
            if (productNew != null) {
                this.info = productNew;
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
                if (info.getGifts() != null) {
                    adapterGift.clear();
                    adapterGift.addAll(info.getGifts());
                }
            }
        });
        viewModel.mItemCheckBooking.observe(this, result -> {
            if (result != null) {
                PublicVariables.itemBooking = result.Data;
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    Integer count = 1;
    String gg = "";

    @SuppressLint("NewApi")
    public void setView(String title, Double price, String description) {
        binding.textCountBuy.setText("1");
        binding.textName.setText(title);
        binding.textPrice.setText(AppUtils.formatNumber("NO").format(price));
//        binding.layoutContent.loadData(info.content, "text/html", "UTF-8");
        Log.d("DECEPTIONTMAIN", info.description + "\n\n\n\n" + gg);
        if (description != null) {
            binding.textDeception.setText(HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY));
        } else {
            binding.textDeception.setVisibility(View.GONE);
        }

        if (info.content != null) {
            gg = info.content.replace("<li>\r\n\t<p>", "<li>").replace("</p>\r\n\t</li>", "</li>");
//            binding.textContent.setText(HtmlCompat.fromHtml(gg, FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE));
            binding.textContent.setText(HtmlCompat.fromHtml(gg, HtmlCompat.FROM_HTML_MODE_LEGACY));
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
        binding.btnBuyNow.setVisibility(View.GONE);
        binding.btnAddCart.setOnClickListener(view1 -> {
            isBuyNow = false;
            CartCondition condition = new CartCondition();
            condition.setNguoiDungMobileID(PublicVariables.UserInfo.getNguoiDungMobileID());
            condition.setSoLuong(count);
            condition.setProductID(info.code);
            condition.setGhiChu("");
            condition.setCreateDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
            condition.setModifiedDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
            viewModel.insertCart(condition);

            if (!isClick) {
                if (itemMain != null) {
                    CartCondition conditionGift = new CartCondition();
                    conditionGift.setNguoiDungMobileID(PublicVariables.UserInfo.getNguoiDungMobileID());
                    conditionGift.setSoLuong(count);
                    conditionGift.setProductID(itemMain.code);
                    conditionGift.setGhiChu("");
                    conditionGift.SanPhamChinhID = info.code;
                    conditionGift.setCreateDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
                    conditionGift.setModifiedDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
                    viewModel.insertCart(conditionGift);
                }
            }
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
            viewModel.insertCart(condition);
            if (!isClick) {
                if (itemMain != null) {
                    CartCondition conditionGift = new CartCondition();
                    conditionGift.setNguoiDungMobileID(PublicVariables.UserInfo.getNguoiDungMobileID());
                    conditionGift.setSoLuong(count);
                    conditionGift.setProductID(itemMain.code);
                    conditionGift.setGhiChu("");
                    conditionGift.SanPhamChinhID = info.code;
                    conditionGift.setCreateDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
                    conditionGift.setModifiedDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
                    viewModel.insertCart(conditionGift);
                }
            }
        });
        String url = "https://gasanphat.com/" + info.photo;

        Glide.with(this)
                .load(url)
                .error(R.drawable.img_no_image)
                .into(binding.imageView);
    }


}
