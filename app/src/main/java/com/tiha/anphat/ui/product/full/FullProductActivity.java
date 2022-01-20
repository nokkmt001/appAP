package com.tiha.anphat.ui.product.full;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tiha.anphat.R;
import com.tiha.anphat.data.AppPreference;
import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.condition.CartCondition;
import com.tiha.anphat.data.entities.condition.ProductCondition;
import com.tiha.anphat.databinding.ActivityFullProductBinding;
import com.tiha.anphat.main.MainActivity;
import com.tiha.anphat.ui.base.BaseTestActivity;
import com.tiha.anphat.ui.booking.BookingActivity;
import com.tiha.anphat.ui.product.ProductContract;
import com.tiha.anphat.ui.product.ProductPresenter;
import com.tiha.anphat.ui.product.detail.DetailAdapter;
import com.tiha.anphat.utils.AppConstants;
import com.tiha.anphat.utils.AppUtils;
import com.tiha.anphat.utils.CommonUtils;
import com.tiha.anphat.utils.PublicVariables;
import com.tiha.anphat.utils.TestConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FullProductActivity extends BaseTestActivity<ActivityFullProductBinding> implements ProductContract.View {
    ActivityFullProductBinding bd;

    String title = "";
    ProductPresenter presenter;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private static final int PAGE_START = 1;
    private static int PAGE_RECORD = 20;
    private int currentPage = PAGE_START;
    ProductCondition condition = new ProductCondition();
    TextView textError;
    DetailAdapter adapter;
    EditText inputSearch;
    ImageView imageDelete;
    private Timer timer;
    ProductInfo info;
    MainActivity activity;
    Double inventory = 0.0; // tồn kho
    Boolean isBuyNow = false;
    AppPreference preference;
    String categoryID = "";

    @Override
    public ActivityFullProductBinding getViewBinding() {
        return bd = ActivityFullProductBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        categoryID = bundle.getString("ITEM");
        inputSearch = bind(R.id.inputSearch);
        imageDelete = bind(R.id.imageDelete);
        adapter = new DetailAdapter(this, new ArrayList<ProductInfo>(), title);
        textError = bind(R.id.textError);
        bd.rlvProduct.setLayoutManager(new GridLayoutManager(this, 2));
        bd.rlvProduct.setAdapter(adapter);
        bd.rlvProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isLoading = true;
                currentPage += 1;
                loadNextPage();
            }
        });


        adapter.setClickListener((view1, position) -> {
            info = adapter.getItem(position);
            showBottomSheet(info.getImageBitMap(), info.getProduct_Name(), AppUtils.formatNumber("N0").format(info.getGiaBanLe()),
                    inventory, info.getDescription());
        });
        Search();
    }

    public void Search() {
        bd.layoutSearch.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().isEmpty()) imageDelete.setVisibility(View.VISIBLE);
                else imageDelete.setVisibility(View.GONE);
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(FullProductActivity.this::initData);
                    }

                }, AppConstants.DELAY_FIND_DATA);
            }
        });

        imageDelete.setOnClickListener(view -> inputSearch.setText(""));
    }

    @Override
    protected void initData() {
        preference = new AppPreference(this);
        presenter = new ProductPresenter(this);
        condition.setBegin(PAGE_START);
        condition.setUserName("TIHA");
        condition.setNhomLoaiHang(categoryID);
        if (!TextUtils.isEmpty(inputSearch.getText().toString())) {
            condition.setEnd(100000);
        } else {
            condition.setEnd(PAGE_RECORD);
        }
        condition.setTextSearch(inputSearch.getText().toString());
        presenter.GetListProduct(condition);
    }

    public void loadNextPage() {
        condition.setBegin(condition.getEnd() + 1);
        condition.setEnd(condition.getEnd() + PAGE_RECORD);
        presenter.GetListProduct(condition);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetListProductSuccess(List<ProductInfo> list, Integer total) {
        if (condition.getBegin() == 1) {
            adapter.clear();
        }
        adapter.addAll(list);
        showProgressDialog(false);
    }

    @Override
    public void onGetListProductError(String error) {
        CommonUtils.showMessageError(this, error);
        showProgressDialog(false);
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

    Integer count = 1;

    private void showBottomSheet(String imageBitMap, String title, String price, final Double number, String description) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_chose_product, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        assert bottomSheet != null;
        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        dialog.show();
        final ImageView imageMain = bind(R.id.imageView);
        final ImageView imageClose = bind(R.id.imageClose);
        imageClose.setOnClickListener(view14 -> dialog.cancel());
        TextView tvTitle = bind(R.id.textName);
        TextView tvPrice = bind(R.id.textPrice);
        TextView tvDeception = bind(R.id.textDeception);
        final TextView tvCountBuy = bind(R.id.textCountBuy);
        Button btAddCart = bind(R.id.btnAddCart);
        Button btnBuyNow = bind(R.id.btnBuyNow);
        ImageView imgAdd = bind(R.id.imageAdd);
        ImageView imgMinus = bind(R.id.imageMinus);
        count = 1;
        tvCountBuy.setText(count.toString());
        tvTitle.setText(title);
        tvPrice.setText(price);
        tvDeception.setText(description);

        final Date date = new Date(System.currentTimeMillis());
        imgAdd.setOnClickListener(view12 -> {
            count = count + 1;
            tvCountBuy.setText(count.toString());
        });
        imgMinus.setOnClickListener(view13 -> {
            if (count != 1) {
                count = count - 1;
            }
            tvCountBuy.setText(count.toString());
        });
        btAddCart.setVisibility(View.VISIBLE);
        btnBuyNow.setVisibility(View.VISIBLE);
        btAddCart.setOnClickListener(view1 -> {
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
        btnBuyNow.setOnClickListener(v -> {
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
                imageMain.setImageBitmap(bitmap);
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

}
