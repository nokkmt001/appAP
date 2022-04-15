package com.anphat.supplier.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.CommonData;
import com.anphat.supplier.data.entities.BannerInfo;
import com.anphat.supplier.data.entities.CategoryNew;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.data.entities.kho.KhoInfo;
import com.anphat.supplier.data.network.apiretrofit.ProductCondition;
import com.anphat.supplier.ui.base.BaseEventClick;
import com.anphat.supplier.ui.base.BaseFragment;
import com.anphat.supplier.ui.booking.BookingActivity;
import com.anphat.supplier.ui.category.DetailCategoryFragment;
import com.anphat.supplier.ui.home.branch.BranchContract;
import com.anphat.supplier.ui.home.choose.FullCategoryActivity;
import com.anphat.supplier.ui.product.detail.DetailAdapter;
import com.anphat.supplier.ui.product.detail.DetailTwoAdapter;
import com.anphat.supplier.ui.product.full.ChooseProductFragment;
import com.anphat.supplier.ui.sms.newsfeed.ViewImageAds;
import com.anphat.supplier.ui.viewmodel.ProductViewModel;
import com.anphat.supplier.utils.AppConstants;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.TestConstants;
import com.anphat.supplier.utils.adapterimage.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements BranchContract.View, HomeContract.View {
    List<KhoInfo> listDataBranch = new ArrayList<>();
    CategoryMainAdapter adapterCategory;
    RecyclerView rclCategory, rclMain, rclAllProduct;
    DetailAdapter adapter;
    HomePresenter presenterProduct;
    EditText inputSearch;
    ImageView imageDelete;
    AppPreference preference;
    Boolean isBuyNow = false;
    ViewImageAds adsAdapter;
    AutoScrollViewPager rclImage;
    LinearLayout layoutConfig;
    TextView textMore, textMoreAllProduct;
    final int SHOW = 1;
    DetailTwoAdapter adapterAllProduct;

    public boolean isAll = false;
    ReceiverSearch searchMain;
    public BaseEventClick.OnClickListener listener;
    ProductViewModel viewModel;
    private Integer pageMain = 1;

    private Integer pageMainPromotion = 1;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private static final int PAGE_START = 0;
    private static final int PAGE_RECORD = 20;
    private int currentPage = PAGE_START;
    ProductCondition condition = new ProductCondition();

    public void setClick(BaseEventClick.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        showProgressDialog(true);
        searchMain = new ReceiverSearch();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TestConstants.ACTION_MAIN_HOME);
        getContext().registerReceiver(searchMain, intentFilter);

        preference = new AppPreference(getActivity());
        rclCategory = bind(view, R.id.rclCategory);


        textMoreAllProduct = bind(view, R.id.textMoreAllProduct);

        textMore = bind(view, R.id.textMore);
        /**
         * layout detail product
         */
        rclAllProduct = bind(view, R.id.rclAllProduct);
        rclAllProduct.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rclAllProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentPage += 1;
                loadNextPage();
            }
        });
        adapterAllProduct = new DetailTwoAdapter(getContext(), new ArrayList<>(), "");
        adapterAllProduct.setAll(true);
        rclAllProduct.setAdapter(adapterAllProduct);

        imageDelete = bind(view, R.id.imageDelete);
        rclImage = bind(view, R.id.rclImage);
        layoutConfig = bind(view, R.id.layoutConfig);
        inputSearch = bind(view, R.id.inputSearch);
        adapterCategory = new CategoryMainAdapter();
        rclCategory.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rclCategory.setAdapter(adapterCategory);
        adapterCategory.setOnClickListener((view1, position) -> {
            CategoryNew info = adapterCategory.getItem(position);
            Intent intentB = new Intent();
            intentB.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
            intentB.putExtra("eventName", "hh");
            getContext().sendBroadcast(intentB);
            StartDetailCategory(info);
        });
        /**
         * layout promotion
         */
        rclMain = bind(view, R.id.rclMain);

        adapter = new DetailAdapter(getActivity(), new ArrayList<>(), "");
        adapter.setAll(false);
        rclMain.setAdapter(adapter);

        Search();
        layoutConfig.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), FullCategoryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, SHOW);
        });

        adapter.setClickListener((view12, position) -> {
            StartDetailFragment((int) adapter.getItem(position).id);
            Intent intentB = new Intent();
            intentB.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
            intentB.putExtra("eventName", "hh");
            getContext().sendBroadcast(intentB);
        });
        textMore.setOnClickListener(v -> {
            if (AppPreference.getProductPromotion() == null) return;
            StartFullProduct(AppPreference.getProductPromotion());
            Intent intentB = new Intent();
            intentB.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
            intentB.putExtra("eventName", "hh");
            getContext().sendBroadcast(intentB);
        });
        textMoreAllProduct.setOnClickListener(v -> {
            if (AppPreference.getProductFull() == null) return;
            StartFullProduct(AppPreference.getProductFull());
            Intent intentB = new Intent();
            intentB.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
            intentB.putExtra("eventName", "hh");
            getContext().sendBroadcast(intentB);
        });

        adapterAllProduct.setClickListener((view13, position) -> {
            StartDetailFragment((int) adapter.getItem(position).id);
            Intent intentB = new Intent();
            intentB.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
            intentB.putExtra("eventName", "hh");
            getContext().sendBroadcast(intentB);
        });

        adsAdapter = new ViewImageAds(getContext(), new ArrayList<>());
        rclImage.setAdapter(adsAdapter);
    }

    public void setSearchMain(String text) {
        adapterAllProduct.getFilter().filter(text);
    }

    private void addAds(List<BannerInfo> list) {
        adsAdapter.AddAll(list);
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
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                new Handler().postDelayed(() -> {
                    adapter.getFilter().filter(editable);
                    adapterAllProduct.getFilter().filter(editable);
                }, AppConstants.DELAY_FIND_DATA_SEARCH);
            }
        });

        imageDelete.setOnClickListener(view -> inputSearch.setText(""));
    }

    @Override
    protected void initData() {
        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        condition.setBegin(PAGE_START);
        condition.setEnd(PAGE_RECORD);
        viewModel.getListAllProduct(condition.getBegin(), condition.getEnd());
        viewModel.getBanner();
        viewModel.getListProductPromotion();
        presenterProduct = new HomePresenter(this);

        List<CategoryNew> list = new ArrayList<>();
        for (CategoryNew item : AppPreference.getCategory()) {
            if (item.parent_id == 0) {
                list.add(item);
            }
        }

        if (list.size() == 0) {
            return;
        }

        adapterCategory.clear();
        adapterCategory.addAll(list);
    }

    public void loadNextPage() {
        condition.setBegin(condition.getEnd());
        condition.setEnd(condition.getEnd() + PAGE_RECORD);
        viewModel.getListAllProduct(condition.getBegin(), condition.getEnd());
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.getDataProductSuccess().observe(this, productNews -> {
            if (productNews != null) {
                if (condition.getBegin() == 0) {
                    adapterAllProduct.clear();
                }
                adapterAllProduct.addAll(productNews);

            }
            showProgressDialog(false);
        });

        viewModel.getmItemBanner().observe(this, list -> {
            if (list != null) {
                adsAdapter.AddAll(list);
            }
        });

        viewModel.getmItemSlider().observe(this, list -> {
            if (list != null) {
                adsAdapter.AddAll(list);
            }
        });
        viewModel.getDataProductPromotion().observe(this, productNews -> {
            if (productNews != null) {
                adapter.clear();
                adapter.addAll(productNews);
            }
        });
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

    @Override
    public void onGetListProductSuccess(List<ProductNew> list) {
        PublicVariables.listKM = list;
        AppPreference.saveProduct(list);
        AppPreference.saveAllProduct(list);
        CommonData.data = list;
        adapterAllProduct.clear();
        adapterAllProduct.addAll(list);
        showProgressDialog(false);
    }

    @Override
    public void onGetListProductError(String error) {
        showMessage(error);
        AppPreference.clearProductPromotion();
        showProgressDialog(false);

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

    @Override
    public void onGetListBannerSuccess(List<BannerInfo> list) {
        addAds(list);
    }

    @Override
    public void onGetListBannerError(String error) {
        showMessage(error);
    }

    @Override
    public void onGetListProductPromotionSuccess(List<ProductNew> list) {
        AppPreference.saveProductPromotion(list);
        CommonData.dataPromotions = list;
        adapter.clear();
        adapter.addAll(list);
    }

    @Override
    public void onGetListProductPromotionError(String error) {
        showMessage(error);
    }

    private void StartDetailFragment(Integer ID) {
        ChooseProductFragment nextFrag = new ChooseProductFragment(ID, true);
        CommonFM.fragmentTwo = nextFrag;
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container, nextFrag, "one")
                .hide(CommonFM.fragment)
                .addToBackStack(null)
                .commit();
    }

    private void StartDetailCategory(CategoryNew info) {
        DetailCategoryFragment nextFrag = new DetailCategoryFragment(info);
        CommonFM.fragmentThree = nextFrag;
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container, nextFrag, "three")
                .hide(CommonFM.fragment)
                .addToBackStack(null)
                .commit();
    }

    private void StartFullProduct(List<ProductNew> list) {
        ShowFragment nextFrag = new ShowFragment(list);
        CommonFM.fragmentFour = nextFrag;
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container, nextFrag, "three")
                .hide(CommonFM.fragment)
                .addToBackStack(null)
                .commit();
    }

    private class ReceiverSearch extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle == null) return;
            String eventTextSearch = bundle.getString("eventSearch");
            if (eventTextSearch != null) {
                setSearchMain(eventTextSearch);
            }
        }
    }

}
