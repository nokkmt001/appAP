package com.anphat.supplier.ui.product.detail;

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
import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.ProductInfo;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.data.entities.condition.ProductCondition;
import com.anphat.supplier.main.MainActivity;
import com.anphat.supplier.ui.base.BaseFragment;
import com.anphat.supplier.ui.booking.BookingActivity;
import com.anphat.supplier.ui.product.ProductContract;
import com.anphat.supplier.ui.product.ProductPresenter;
import com.anphat.supplier.utils.AppConstants;
import com.anphat.supplier.utils.AppUtils;
import com.anphat.supplier.utils.CommonUtils;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.TestConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("SetTextI18n")
public class DetailProductFragment extends BaseFragment implements ProductContract.View {
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
    RecyclerView rlv;
    EditText inputSearch;
    ImageView imageDelete;
    private Timer timer;
    ProductInfo info;
    MainActivity activity;
    Double inventory = 0.0; // tồn kho
    Boolean isBuyNow = false;
    AppPreference preference;

    public DetailProductFragment(String textTitle) {
        title = textTitle;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_detail_product;
    }

    @Override
    protected void initView(View view) {
        activity = new MainActivity();
        inputSearch = bind(view, R.id.inputSearch);
        imageDelete = bind(view, R.id.imageDelete);
        adapter = new DetailAdapter(getActivity(), new ArrayList<>(), title);
        textError = bind(view, R.id.textError);
        rlv = bind(view, R.id.rlvProduct);
        rlv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rlv.setAdapter(adapter);
        rlv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isLoading = true;
                currentPage += 1;
                loadNextPage();
            }
        });

        adapter.setClickListener((view1, position) -> {
//            info = adapter.getItem(position);
//            showBottomSheet(info.getImageBitMap(), info.getProduct_Name(), AppUtils.formatNumber("N0").format(info.getGiaBanLe()),
//                    inventory, info.getDescription());
        });
        Search();

    }

    public void Search() {
        inputSearch.addTextChangedListener(new TextWatcher() {
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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initData();
//                                onLoadSearch(editable.toString());
                            }
                        });
                    }

                }, AppConstants.DELAY_FIND_DATA);
            }
        });

        imageDelete.setOnClickListener(view -> inputSearch.setText(""));
    }

    @Override
    protected void initData() {
        preference = new AppPreference(getContext());
        presenter = new ProductPresenter(this);
        condition.setBegin(PAGE_START);
        condition.setUserName("TIHA");
        condition.setNhomLoaiHang(title);
        if (!TextUtils.isEmpty(inputSearch.getText().toString())) {
            condition.setEnd(100000);
        } else {
            condition.setEnd(PAGE_RECORD);
        }
        condition.setTextSearch(inputSearch.getText().toString());
        presenter.GetListProduct(condition);
//        showProgressDialog(true);
    }

    public void loadNextPage() {
        condition.setBegin(condition.getEnd() + 1);
        condition.setEnd(condition.getEnd() + PAGE_RECORD);
        presenter.GetListProduct(condition);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onGetListProductSuccess(List<ProductInfo> list, Integer total) {
        if (condition.getBegin() == 1) {
            adapter.clear();
        }
//        adapter.addAll(list);
        showProgressDialog(false);
    }

    @Override
    public void onGetListProductError(String error) {
        CommonUtils.showMessageError(getActivity(), error);
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
        Toast.makeText(getContext(), R.string.add_cart_success, Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        intent.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
        intent.putExtra("eventName", TestConstants.RECEIVE_ThayDoiGioHang);
        intent.putExtra("ItemGioHang", info);
        getActivity().sendBroadcast(intent);
        if (isBuyNow) {
            Intent intent1 = new Intent(getContext(), BookingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("ID",info.getID().toString());
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
        showProgressDialog(false);
        showMessage(error);
    }

    Integer count = 1;

    private void showBottomSheet(String imageBitMap, String title, String price, final Double number, String description) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_chose_product, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(view);
        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        assert bottomSheet != null;
        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        dialog.show();
        final ImageView imageMain = bind(view, R.id.imageView);
        final ImageView imageClose = bind(view, R.id.imageClose);
        imageClose.setOnClickListener(view14 -> dialog.cancel());
        TextView tvTitle = bind(view, R.id.textName);
        TextView tvPrice = bind(view, R.id.textPrice);
        TextView tvDeception = bind(view, R.id.textDeception);
        final TextView tvCountBuy = bind(view, R.id.textCountBuy);
        Button btAddCart = bind(view, R.id.btnAddCart);
        Button btnBuyNow = bind(view, R.id.btnBuyNow);
        ImageView imgAdd = bind(view, R.id.imageAdd);
        ImageView imgMinus = bind(view, R.id.imageMinus);
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
            if (PublicVariables.itemBooking != null){
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
        Picasso.with(getContext()).load(url).into(target);
    }
}