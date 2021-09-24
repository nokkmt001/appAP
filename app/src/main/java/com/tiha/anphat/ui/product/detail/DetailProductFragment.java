package com.tiha.anphat.ui.product.detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.condition.CartCondition;
import com.tiha.anphat.data.entities.condition.ProductCondition;
import com.tiha.anphat.main.MainActivity;
import com.tiha.anphat.ui.base.BaseEventClick;
import com.tiha.anphat.ui.base.BaseFragment;
import com.tiha.anphat.ui.product.ProductContract;
import com.tiha.anphat.ui.product.ProductPresenter;
import com.tiha.anphat.utils.AppConstants;
import com.tiha.anphat.utils.AppUtils;
import com.tiha.anphat.utils.CommonUtils;
import com.tiha.anphat.utils.PublicVariables;

import org.jetbrains.annotations.NotNull;

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

    public DetailProductFragment(String textTitle) {
        title = textTitle;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_product;
    }

    @Override
    protected void onInit(View view) {
        activity = new MainActivity();
        inputSearch = bind(view, R.id.inputSearch);
        imageDelete = bind(view, R.id.imageDelete);
        adapter = new DetailAdapter(getActivity(), new ArrayList<ProductInfo>(), title);
        textError = bind(view, R.id.textError);
        rlv = bind(view, R.id.rlvProduct);
        rlv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rlv.setHasFixedSize(true);
        rlv.setItemAnimator(new DefaultItemAnimator());
        rlv.setAdapter(adapter);
        rlv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isLoading = true;
                currentPage += 1;
                LoadNextPage();
            }
        });

        adapter.setClickListener(new BaseEventClick.OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                info = adapter.getItem(position);
                presenter.GetImageByProDuctID(info.getProduct_ID());
            }
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
                                onLoadSearch(editable.toString());
                            }
                        });
                    }

                }, AppConstants.DELAY_FIND_DATA);
            }
        });
    }

    public void onLoadSearch(String text) {
        condition = new ProductCondition();
        condition.setBegin(0);
        condition.setEnd(100000);
        condition.setUserName("TIHA");
        condition.setNhomLoaiHang(title);
        condition.setTextSearch(text);
        presenter.GetListProduct(condition);
        adapter.clear();
    }

    @Override
    protected void onLoadData() {
        presenter = new ProductPresenter(this);
        condition.setBegin(PAGE_START);
        condition.setEnd(PAGE_RECORD);
        condition.setUserName("TIHA");
        condition.setNhomLoaiHang(title);
        condition.setTextSearch("");
        presenter.GetListProduct(condition);
    }

    public void LoadNextPage() {
        condition.setBegin(condition.getEnd() + 1);
        condition.setEnd(condition.getEnd() + PAGE_RECORD);
        presenter.GetListProduct(condition);
    }

    @Override
    protected void configToolbar() {

    }

    @Override
    public void onClick(View view) {

    }

    public void showError(final boolean isShow) {
        textError.setVisibility(isShow ? View.VISIBLE : View.GONE);
        rlv.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onGetListProductSuccess(List<ProductInfo> list, Integer total) {
        adapter.addAll(list);
        if (list.size() == 0) {
        } else {
            double phanDu = Double.parseDouble(String.valueOf(total)) % PAGE_RECORD;
            int phanNguyen = (Integer.parseInt(String.valueOf(total)) / PAGE_RECORD);
            TOTAL_PAGES = (phanDu > 0) ? phanNguyen + 1 : phanNguyen;
            if (currentPage < TOTAL_PAGES) {

            } else isLastPage = true;
        }

    }

    @Override
    public void onGetListProductError(String error) {
        CommonUtils.showMessageError(getActivity(), error);
        showProgressDialog(false);
    }

    @Override
    public void onGetImageByProDuctIDSuccess(String imageBitmap) {
        showBottomSheet(imageBitmap, info.getProduct_Name(), AppUtils.formatNumber("N0").format(info.getGiaBanLe()),
                AppUtils.formatNumber("N0").format(info.getSLNhieu()));
    }

    @Override
    public void onGetImageByProDuctIDError(String error) {
        showBottomSheet(null, info.getProduct_Name(), AppUtils.formatNumber("N0").format(info.getGiaBanLe()),
                AppUtils.formatNumber("N0").format(info.getSLNhieu()));
    }

    @Override
    public void onInsertCartSuccess(CartCondition info) {
        activity.onLoadCartListener();
    }

    @Override
    public void onInsertCartError(String error) {
        showMessage(error);
    }

    Integer count = 1;

    private void showBottomSheet(String imageBitMap, String title, String price, final String number) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_chose_product, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(view);
        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        assert bottomSheet != null;
        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        dialog.show();
        final RelativeLayout layoutCountBuy = bind(view, R.id.layoutCountBuy);
        final ImageView imageMain = bind(view, R.id.imageView);
        TextView tvTitle = bind(view, R.id.textName);
        TextView tvPrice = bind(view, R.id.textPrice);
        TextView tvDeception = bind(view, R.id.textDeception);
        final TextView tvCountBuy = bind(view, R.id.textCountBuy);
        Button btAddCart = bind(view, R.id.btnAddCart);
        Button btnBuyNow = bind(view, R.id.btnBuy);
        Button btnEmpty = bind(view, R.id.btnEmpty);
        ImageView imgAdd = bind(view, R.id.imageAdd);
        ImageView imgMinus = bind(view, R.id.imageMinus);
        count = 1;
        tvCountBuy.setText(count.toString());
        tvTitle.setText(title);
        tvPrice.setText(price);
        if (!"0".equals(number)) {
            layoutCountBuy.setVisibility(View.VISIBLE);
        } else {
            layoutCountBuy.setVisibility(View.GONE);
        }
        final Date date = new Date(System.currentTimeMillis());
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count != Integer.parseInt(number)) {
                    count = count + 1;
                }
                tvCountBuy.setText(count.toString());
            }
        });
        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count != 1) {
                    count = count - 1;
                }
                tvCountBuy.setText(count.toString());
            }
        });
        if (number.equals("0")) {
            btnEmpty.setVisibility(View.VISIBLE);
            btAddCart.setVisibility(View.GONE);
            btnBuyNow.setVisibility(View.GONE);
        } else {
            btnEmpty.setVisibility(View.GONE);
            btAddCart.setVisibility(View.VISIBLE);
            btnBuyNow.setVisibility(View.VISIBLE);
        }
        btAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartCondition condition = new CartCondition();
                condition.setNguoiDungMobileID(PublicVariables.UserInfo.getNguoiDungMobileID());
                condition.setSoLuong(count);
                condition.setProductID(info.getProduct_ID());
                condition.setGhiChu("");
                condition.setCreateDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
                condition.setModifiedDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
                presenter.InsertCart(condition);
            }
        });
        if (imageBitMap != null) {
            Glide.with(getActivity()).asBitmap()
                    .load(AppUtils.formatStringToBitMap(imageBitMap))
                    .apply(new RequestOptions().override(10, 10))
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull @NotNull Bitmap resource, @Nullable @org.jetbrains.annotations.Nullable
                                Transition<? super Bitmap> transition) {
                            imageMain.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
        } else {
            imageMain.setImageResource(R.drawable.img_no_image);
        }

    }
}
