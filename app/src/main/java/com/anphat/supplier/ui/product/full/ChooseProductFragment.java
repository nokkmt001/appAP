package com.anphat.supplier.ui.product.full;

import static androidx.core.text.HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anphat.supplier.data.entities.BannerInfo;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.ui.product.detail.DetailAdapter;
import com.anphat.supplier.utils.CommonUtils;
import com.bumptech.glide.Glide;
import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.ui.base.BaseFragment;
import com.anphat.supplier.ui.booking.BookingActivity;
import com.anphat.supplier.ui.home.CommonFM;
import com.anphat.supplier.ui.home.HomeContract;
import com.anphat.supplier.ui.home.HomePresenter;
import com.anphat.supplier.utils.AppUtils;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.TestConstants;
import com.anphat.supplier.utils.adapterimage.ViewImageActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChooseProductFragment extends BaseFragment implements HomeContract.View, DetailPrContract.View {
    HomePresenter presenterProduct;
    Integer ID;
    DetailProductPresenter presenterOne;
    ProductNew info;

    TextView textDetail, textCountBuy, textName, textPrice, textDeception, textContent;
    ImageView imageBack, imageCart, imageView, imageAdd, imageMinus;
    Button btnAddCart, btnBuyNow;
    Boolean isBuyNow = false;
    DetailAdapter adapter;
    RecyclerView rclMain;
    EditText textTitle;
    LinearLayout layoutOne, layoutTwo;
    NestedScrollView nestedScrollView;

    public ChooseProductFragment(Integer ID) {
        this.ID = ID;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_choose_product;
    }

    @Override
    protected void initView(View view) {
        layoutOne = bind(view, R.id.layoutOne);
        layoutTwo = bind(view, R.id.layoutTwo);
        imageCart = bind(view, R.id.imageCart);
        textDetail = bind(view, R.id.textDetail);
        textTitle = bind(view, R.id.textTitle);
        imageBack = bind(view, R.id.imageBack);
        imageView = bind(view, R.id.imageView);
        rclMain = bind(view, R.id.rclMain);
        imageAdd = bind(view, R.id.imageAdd);
        imageMinus = bind(view, R.id.imageMinus);
        btnAddCart = bind(view, R.id.btnAddCart);
        btnBuyNow = bind(view, R.id.btnBuyNow);
        nestedScrollView = bind(view, R.id.nestedScrollView);
        textCountBuy = bind(view, R.id.textCountBuy);
        textName = bind(view, R.id.textName);
        textPrice = bind(view, R.id.textPrice);
        textDeception = bind(view, R.id.textDeception);
        textContent = bind(view, R.id.textContent);

        adapter = new DetailAdapter(getActivity(), new ArrayList<>(), "");
        rclMain.setAdapter(adapter);
        rclMain.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        adapter.setAll(true);
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ViewImageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle bundle = new Bundle();
            bundle.putString("PATHIMAGE", info.photo);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        imageBack.setOnClickListener(v -> {
            Intent intentB = new Intent();
            intentB.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
            intentB.putExtra("eventName", "kk");
            getContext().sendBroadcast(intentB);
            StartFragmentHome();
        });

        adapter.setClickListener((view1, position) -> {
            info = adapter.getItem(position);
            setView(info.title, info.price, info.description);
            nestedScrollView.scrollTo(0, 0);
        });

        CommonUtils.ActionbarSearch(getContext(), textTitle, imageCart, imageBack, imageCart, text -> {
            adapter.getFilter().filter(text);
            if (text.length() == 0) {
                initData();
                showFooter(true, true);
            } else {
                showFooter(false, true);
            }
        });

    }

    private void showFooter(boolean isShow, boolean footer) {
        layoutOne.setVisibility(isShow ? View.VISIBLE : View.GONE);
        textDetail.setVisibility(isShow ? View.VISIBLE : View.GONE);
        layoutTwo.setVisibility(footer ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void initData() {
        presenterOne = new DetailProductPresenter(this);
        presenterProduct = new HomePresenter(this);
        presenterOne.checkBooking();
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
    public void onGetListBannerSuccess(List<BannerInfo> list) {

    }

    @Override
    public void onGetListBannerError(String error) {

    }

    @Override
    public void onGetProductSuccess(ProductNew info) {
        this.info = info;
        setView(info.title, info.price, info.description);
        if (info.getProducts() != null) {
            adapter.clear();
            adapter.addAll(info.getProducts());
            showFooter(true, true);
        } else {
            showFooter(true, false);
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

    @SuppressLint("NewApi")
    public void setView(String title, Double price, String description) {
        textCountBuy.setText("1");
        textName.setText(title);
        textPrice.setText(AppUtils.formatNumber("NO").format(price));
        textDeception.setText(Html.fromHtml(description));

        String gg = info.content.replace("<li>\r\n\t<p>", "<li>").replace("</p>\r\n\t</li>", "</li>");

        try {
            Document doc = Jsoup.parse(gg);
            Element inner = doc.getElementById("");
        } catch (Exception e1) {
        }
        textContent.setText(Html.fromHtml(gg, FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE));
        Log.d("CONTENTMAIN", info.content + "\n\n\n\n" + gg);
        count = 1;
        Log.d("CONTENT", gg);
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
