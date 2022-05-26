package com.anphat.supplier.ui.product.full;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.anphat.supplier.data.entities.gift.GiftInfo;
import com.anphat.supplier.databinding.ActivityChooseProductBinding;
import com.anphat.supplier.ui.base.BaseMVVMFragment;
import com.anphat.supplier.ui.cart.CartActivity;
import com.anphat.supplier.ui.home.HomeFragment;
import com.anphat.supplier.ui.product.detail.DetailAdapter;
import com.anphat.supplier.viewmodel.DetailViewModel;
import com.bumptech.glide.Glide;
import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.ui.booking.BookingActivity;
import com.anphat.supplier.ui.home.CommonFM;
import com.anphat.supplier.utils.AppUtils;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.TestConstants;
import com.anphat.supplier.utils.adapterimage.ViewImageActivity;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Date;

public class ChooseProductFragment extends BaseMVVMFragment<ActivityChooseProductBinding, DetailViewModel> {
    Integer ID;
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
        showProgressDialog(true);
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
            adapterGift.clear();
            adapterGift.addAll(info.gifts);
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
    public ActivityChooseProductBinding getViewBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return ActivityChooseProductBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initData() {
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
                viewModel.GetListAllCart();
                Toast.makeText(getContext(), R.string.add_cart_success, Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
                intent.putExtra("eventName", TestConstants.RECEIVE_ThayDoiGioHang);
                intent.putExtra("ItemGioHang", result.Data);
                getActivity().sendBroadcast(intent);
                if (isBuyNow) {
                    Intent intent1 = new Intent(getContext(), BookingActivity.class);
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
            showProgressDialog(false);
        });
        viewModel.mItemCheckBooking.observe(this, result -> {
            if (result != null) {
                PublicVariables.itemBooking = result.Data;
            }
        });
        viewModel.itemListCart.observe(this, result ->
                binding.layoutHeader.layoutCart.textNumberCart.setText(String.valueOf(result.size())));
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

//            if (!isClick) {
//                if (itemMain != null) {
//                    CartCondition conditionGift = new CartCondition();
//                    conditionGift.setNguoiDungMobileID(PublicVariables.UserInfo.getNguoiDungMobileID());
//                    conditionGift.setSoLuong(count);
//                    conditionGift.setProductID(itemMain.code);
//                    conditionGift.setGhiChu("");
//                    conditionGift.SanPhamChinhID = info.code;
//                    conditionGift.setCreateDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
//                    conditionGift.setModifiedDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
//                    viewModel.insertCart(conditionGift);
//                }
//            }
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
//            if (!isClick) {
//                if (itemMain != null) {
//                    CartCondition conditionGift = new CartCondition();
//                    conditionGift.setNguoiDungMobileID(PublicVariables.UserInfo.getNguoiDungMobileID());
//                    conditionGift.setSoLuong(count);
//                    conditionGift.setProductID(itemMain.code);
//                    conditionGift.setGhiChu("");
//                    conditionGift.SanPhamChinhID = info.code;
//                    conditionGift.setCreateDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
//                    conditionGift.setModifiedDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
//                    viewModel.insertCart(conditionGift);
//                }
//            }
        });
        String url = "https://gasanphat.com/" + info.photo;

        Glide.with(this)
                .load(url)
                .error(R.drawable.img_no_image)
                .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(25))
                        .placeholder(R.drawable.ic_loading_anim)
                        .error(R.drawable.img_no_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(binding.imageView);
    }

    private void StartFragmentHome(boolean isHome) {
        if (isHome) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .hide(this)
                    .add(R.id.frame_container, new HomeFragment())
                    .addToBackStack(null)
                    .commit();
        } else {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .hide(this)
                    .show(CommonFM.fragmentThree)
                    .addToBackStack(null)
                    .commit();
        }

    }
}
