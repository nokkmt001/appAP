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

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.BannerInfo;
import com.anphat.supplier.data.entities.CategoryNew;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.kho.KhoInfo;
import com.anphat.supplier.data.network.apiretrofit.ProductCondition;
import com.anphat.supplier.ui.base.BaseEventClick;
import com.anphat.supplier.ui.base.BaseFragment;
import com.anphat.supplier.ui.base.PageScrollListener;
import com.anphat.supplier.ui.category.DetailCategoryFragment;
import com.anphat.supplier.ui.home.choose.FullCategoryActivity;
import com.anphat.supplier.ui.product.detail.DetailAdapter;
import com.anphat.supplier.ui.product.detail.DetailTwoAdapter;
import com.anphat.supplier.ui.product.full.ChooseProductFragment;
import com.anphat.supplier.ui.sms.newsfeed.ViewImageAds;
import com.anphat.supplier.ui.viewmodel.ProductViewModel;
import com.anphat.supplier.utils.AppConstants;
import com.anphat.supplier.utils.TestConstants;
import com.anphat.supplier.utils.adapterimage.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {
    List<KhoInfo> listDataBranch = new ArrayList<>();
    CategoryMainAdapter adapterCategory;
    RecyclerView rclCategory, rclMain, rclAllProduct;
    DetailAdapter adapter;
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

    ReceiverSearch searchMain;
    public BaseEventClick.OnClickListener listener;
    ProductViewModel viewModel;
    private static final int PAGE_START = 0;
    private static final int PAGE_RECORD = 20;
    ProductCondition condition = new ProductCondition();
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private int currentPage = PAGE_START;
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
        /**
         * layout category
         */
        rclCategory = bind(view, R.id.rclCategory);
        rclCategory.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapterCategory = new CategoryMainAdapter();
        rclCategory.setAdapter(adapterCategory);

        textMoreAllProduct = bind(view, R.id.textMoreAllProduct);

        textMore = bind(view, R.id.textMore);
        /**
         * layout detail product
         */
        rclAllProduct = bind(view, R.id.rclAllProduct);
        rclAllProduct.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rclAllProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                loadNextPage();
            }
        });
        adapterAllProduct = new DetailTwoAdapter(getContext(), new ArrayList<>(), "");
        adapterAllProduct.setAll(true);
        rclAllProduct.setAdapter(adapterAllProduct);

        imageDelete = bind(view, R.id.imageDelete);

        layoutConfig = bind(view, R.id.layoutConfig);
        inputSearch = bind(view, R.id.inputSearch);
        /**
         * layout ads
         */
        rclImage = bind(view, R.id.rclImage);
        adsAdapter = new ViewImageAds(getContext(), new ArrayList<>());
        rclImage.setAdapter(adsAdapter);
        /**
         * layout promotion
         */
        rclMain = bind(view, R.id.rclMain);
        adapter = new DetailAdapter(getActivity(), new ArrayList<>(), "");
        adapter.setAll(false);
        rclMain.setAdapter(adapter);

        layoutConfig.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), FullCategoryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, SHOW);
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

        Search();

        setAdapter();
    }

    public void setAdapter(){
        adapter.setClickListener((view12, position) -> {
            StartDetailFragment((int) adapter.getItem(position).id);
            Intent intentB = new Intent();
            intentB.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
            intentB.putExtra("eventName", "hh");
            getContext().sendBroadcast(intentB);
        });

        adapterCategory.setOnClickListener((view1, position) -> {
            CategoryNew info = adapterCategory.getItem(position);
            Intent intentB = new Intent();
            intentB.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
            intentB.putExtra("eventName", "hh");
            getContext().sendBroadcast(intentB);
            StartDetailCategory(info);
        });

        adapterAllProduct.setClickListener((view13, position) -> {
            StartDetailFragment((int) adapter.getItem(position).id);
            Intent intentB = new Intent();
            intentB.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
            intentB.putExtra("eventName", "hh");
            getContext().sendBroadcast(intentB);
        });
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
        viewModel.getListAllCategory();
        viewModel.getListAllProduct(condition.getBegin(), condition.getEnd());
        viewModel.getBanner();
        viewModel.getListProductPromotion();

    }

    public void loadNextPage() {
        isLastPage = false;
        condition.setBegin(condition.getEnd());
        condition.setEnd(condition.getEnd() + PAGE_RECORD);
        viewModel.getListAllProduct(condition.getBegin(), condition.getEnd());
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.mItemListCategory.observe(this, result -> {
            if (result!=null){
                List<CategoryNew> list = new ArrayList<>();
                for (CategoryNew item : result.data) {
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
        });
        viewModel.getDataProductSuccess().observe(this, productNews -> {
            if (productNews != null) {
                if (condition.getBegin() == 0) {
                    adapterAllProduct.clear();
                }
                adapterAllProduct.addAll(productNews);
                double phanDu = Double.parseDouble(String.valueOf(1000)) % PAGE_RECORD;
                int phanNguyen = (Integer.parseInt(String.valueOf(1000)) / PAGE_RECORD);
                TOTAL_PAGES = (phanDu > 0) ? phanNguyen + 1 : phanNguyen;
                if (currentPage < TOTAL_PAGES){

                } else isLastPage = true;
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
