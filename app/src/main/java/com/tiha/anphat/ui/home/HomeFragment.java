package com.tiha.anphat.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tiha.anphat.R;
import com.tiha.anphat.data.AppPreference;
import com.tiha.anphat.data.entities.CategoryInfo;
import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.condition.CartCondition;
import com.tiha.anphat.data.entities.condition.ProductCondition;
import com.tiha.anphat.data.entities.kho.KhoInfo;
import com.tiha.anphat.ui.base.BaseEventClick;
import com.tiha.anphat.ui.base.BaseFragment;
import com.tiha.anphat.ui.booking.BookingActivity;
import com.tiha.anphat.ui.home.branch.BranchContract;
import com.tiha.anphat.ui.home.branch.BranchPresenter;
import com.tiha.anphat.ui.home.choose.FullCategoryActivity;
import com.tiha.anphat.ui.product.detail.DetailAdapter;
import com.tiha.anphat.ui.product.full.ChooseProductActivity;
import com.tiha.anphat.ui.product.full.FullProductActivity;
import com.tiha.anphat.ui.sms.newsfeed.LoadAdsAdapter;
import com.tiha.anphat.utils.AppConstants;
import com.tiha.anphat.utils.AutoScrollRecyclerView;
import com.tiha.anphat.utils.PublicVariables;
import com.tiha.anphat.utils.TestConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends BaseFragment implements BranchContract.View, HomeContract.View {
    BranchPresenter presenter;
    List<KhoInfo> listDataBranch = new ArrayList<>();
    //    CategoryAdapter adapterCategory;
    CategoryMainAdapter adapterCategory;
    RecyclerView rclCategory, rclMain;
    DetailAdapter adapter;
    HomePresenter presenterProduct;
    ProductCondition condition = new ProductCondition();
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private static final int PAGE_START = 1;
    private static int PAGE_RECORD = 20;
    private int currentPage = PAGE_START;
    EditText inputSearch;
    ImageView imageDelete;
    private Timer timer;
    String category = "";
    AppPreference preference;
    Boolean isBuyNow = false;
    ProductInfo info;
    List<CategoryInfo> listAdd;
    LoadAdsAdapter adsAdapter;
    AutoScrollRecyclerView rclImage;
    LinearLayout layoutConfig;
    final int SHOW = 1;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        rclCategory = bind(view, R.id.rclCategory);
        rclMain = bind(view, R.id.rclMain);
        imageDelete = bind(view, R.id.imageDelete);
        rclImage = bind(view, R.id.rclImage);
        layoutConfig = bind(view, R.id.layoutConfig);

        inputSearch = bind(view, R.id.inputSearch);
//        adapterCategory = new CategoryAdapter();
        adapterCategory = new CategoryMainAdapter();
        rclCategory.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rclCategory.setAdapter(adapterCategory);
        adapterCategory.setOnClickListener((view1, position) -> {
//            CategoryInfo info = adapterCategory.getItem(position);
//            category = info.getCategory_ID();
//            onLoadData();
        });

        adapter = new DetailAdapter(getActivity(), new ArrayList<>(), "");
        rclMain.setAdapter(adapter);
        rclMain.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rclMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isLoading = true;
                currentPage += 1;
                loadNextPage();
            }
        });
        Search();
        adsAdapter = new LoadAdsAdapter(getContext());
        rclImage.setAdapter(adsAdapter);
//        adapter.setClickListener((view1, position) -> {
//            info = adapter.getItem(position);
//            showBottomSheet(info.getImageBitMap(), info.getProduct_Name(), AppUtils.formatNumber("N0").format(info.getGiaBanLe()),
//                    0.0, info.getDescription());
//        });

        addAds();
        layoutConfig.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), FullCategoryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, SHOW);
        });
        adapter.setClickListener((view12, position) -> {
            Intent intent = new Intent(getContext(), ChooseProductActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle bundle = new Bundle();
            bundle.putSerializable("ITEM",adapter.getItem(position));
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private void addAds() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("jjjhhh");
        }
        adsAdapter.clear();
        adsAdapter.addAll(list);
        rclImage.startAutoScroll();
        rclImage.setLoopEnabled(true);
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
                if (timer != null) timer.cancel();
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        onLoadData();
                    }
                }, AppConstants.DELAY_FIND_DATA_SEARCH);
            }
        });

        imageDelete.setOnClickListener(view -> inputSearch.setText(""));
    }


    @Override
    protected void initData() {
        try {
            listAdd = new ArrayList<>();
            preference = new AppPreference(getContext());
            presenterProduct = new HomePresenter(this);
            presenter = new BranchPresenter(this);
            listDataBranch = PublicVariables.listKho;
            adapterCategory.clear();
            adapterCategory.setSelect_position(-1);

            if (preference.getPrefCategory() != null) {
                listAdd = new CategoryInfo().getListCategory(preference.getPrefCategory());
            } else {
                listAdd = new ArrayList<>();
                for (int i = 0; i < PublicVariables.listCategory.size(); i++) {
                    if (i < 8) {
                        CategoryInfo item = PublicVariables.listCategory.get(i);
                        item.setCheck(true);
                        listAdd.add(item);
                    }

                }
            }
            adapterCategory.addAll(listAdd);

            PublicVariables.listShowCategory = listAdd;
        } catch (Exception ignored) {

        }


        condition.setBegin(PAGE_START);
        condition.setUserName("TIHA");
        category = PublicVariables.listCategory.get(0).getCategory_ID();
        condition.setNhomLoaiHang("KHUYENMAI");
        if (!TextUtils.isEmpty(inputSearch.getText().toString())) {
            condition.setEnd(100000);
        } else {
            condition.setEnd(PAGE_RECORD);
        }
        condition.setTextSearch(inputSearch.getText().toString());
        presenterProduct.GetListProduct(condition);
        setAdapterCategory();

    }

    private void setAdapterCategory() {
        adapterCategory.setOnClickListener((view, position) -> {
            Intent intent;
            CategoryInfo info = adapterCategory.getItem(position);
            if (info.getCategory_ID().equals("MORE")) {
                intent = new Intent(getContext(), FullCategoryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SHOW);
            } else {
                intent = new Intent(getContext(), FullProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ITEM", info.getCategory_ID());
                intent.putExtras(bundle);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void onLoadData() {
        condition.setBegin(PAGE_START);
        condition.setUserName("TIHA");
        condition.setNhomLoaiHang(category);
        if (!TextUtils.isEmpty(inputSearch.getText().toString())) {
            condition.setEnd(100000);
        } else {
            condition.setEnd(PAGE_RECORD);
        }
        condition.setTextSearch(inputSearch.getText().toString());
        presenterProduct.GetListProduct(condition);
    }

    public void loadNextPage() {
        condition.setBegin(condition.getEnd() + 1);
        condition.setEnd(condition.getEnd() + PAGE_RECORD);
        presenterProduct.GetListProduct(condition);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetListBranchSuccess(List<KhoInfo> list) {
        PublicVariables.listKho = list;
        listDataBranch = list;
    }

    @Override
    public void onGetListBranchError(String error) {
        showMessage(error);
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Để được nhận gợi ý từ cửa hàng gần nhất, vui lòng bật chức năng xác định vị trí.")
                .setCancelable(false)
                .setTitle("VỊ TRÍ")
                .setPositiveButton("CÀI ĐẶT", (dialog, id) -> startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton("HỦY", (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onGetListProductSuccess(List<ProductInfo> list, Integer total) {
        if (condition.getBegin() == 1) {
            if (list.size() == 0) {
                showNoResult();
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
    public void onInsertCartSuccess(CartCondition info) {
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
        } else {
            Toast.makeText(getContext(), R.string.add_cart_success, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInsertCartError(String error) {
        showMessage(error);
    }

    Integer count = 1;

//    private void showBottomSheet(String imageBitMap, String title, String price, final Double number, String description) {
//        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_chose_product, null);
//        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
//        dialog.setContentView(view);
//        View bottomSheet = view.findViewById(R.id.bottom_sheet);
//        assert bottomSheet != null;
//        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
//        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        dialog.show();
//        final ImageView imageMain = bind(view, R.id.imageView);
//        final ImageView imageClose = bind(view, R.id.imageClose);
//        imageClose.setOnClickListener(view14 -> dialog.cancel());
//        TextView tvTitle = bind(view, R.id.textName);
//        TextView tvPrice = bind(view, R.id.textPrice);
//        TextView tvDeception = bind(view, R.id.textDeception);
//        final TextView tvCountBuy = bind(view, R.id.textCountBuy);
//        Button btAddCart = bind(view, R.id.btnAddCart);
//        Button btnBuyNow = bind(view, R.id.btnBuyNow);
//        ImageView imgAdd = bind(view, R.id.imageAdd);
//        ImageView imgMinus = bind(view, R.id.imageMinus);
//        count = 1;
//        tvCountBuy.setText(count.toString());
//        tvTitle.setText(title);
//        tvPrice.setText(price);
//        tvDeception.setText(description);
//
//        final Date date = new Date(System.currentTimeMillis());
//        imgAdd.setOnClickListener(view12 -> {
//            count = count + 1;
//            tvCountBuy.setText(count.toString());
//        });
//        imgMinus.setOnClickListener(view13 -> {
//            if (count != 1) {
//                count = count - 1;
//            }
//            tvCountBuy.setText(count.toString());
//        });
//        btAddCart.setVisibility(View.VISIBLE);
//        btnBuyNow.setVisibility(View.VISIBLE);
//        btAddCart.setOnClickListener(view1 -> {
//            isBuyNow = false;
//            CartCondition condition = new CartCondition();
//            condition.setNguoiDungMobileID(PublicVariables.UserInfo.getNguoiDungMobileID());
//            condition.setSoLuong(count);
//            condition.setProductID(info.getProduct_ID());
//            condition.setGhiChu("");
//            condition.setCreateDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
//            condition.setModifiedDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
//            presenterProduct.InsertCart(condition);
//        });
//        btnBuyNow.setOnClickListener(v -> {
//            if (preference.getBooking() != null && preference.getBooking().length() > 0) {
//                showMessage(getString(R.string.error_dont_booking));
//                return;
//            }
//            isBuyNow = true;
//            CartCondition condition = new CartCondition();
//            condition.setNguoiDungMobileID(PublicVariables.UserInfo.getNguoiDungMobileID());
//            condition.setSoLuong(count);
//            condition.setProductID(info.getProduct_ID());
//            condition.setGhiChu("");
//            condition.setCreateDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
//            condition.setModifiedDate(AppUtils.formatDateToString(date, "yyyy-MM-dd'T'HH:mm:ss"));
//            presenterProduct.InsertCart(condition);
//        });
//        String url = "https://i.ibb.co/ZTVvwRc/gas-test.png";
//
//        Target target = new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                imageMain.setImageBitmap(bitmap);
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//            }
//        };
//        Picasso.with(getContext()).load(url).into(target);
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SHOW) {
                adapterCategory.clear();
                adapterCategory.addAll(PublicVariables.listShowCategory);
            }
        }
    }
}


