package com.anphat.supplier.ui.home;

import android.content.Intent;
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

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.BannerInfo;
import com.anphat.supplier.data.entities.CategoryNew;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.data.entities.kho.KhoInfo;
import com.anphat.supplier.ui.base.BaseEventClick;
import com.anphat.supplier.ui.base.BaseFragment;
import com.anphat.supplier.ui.booking.BookingActivity;
import com.anphat.supplier.ui.category.DetailCategoryFragment;
import com.anphat.supplier.ui.home.branch.BranchContract;
import com.anphat.supplier.ui.home.choose.FullCategoryActivity;
import com.anphat.supplier.ui.product.detail.DetailAdapter;
import com.anphat.supplier.ui.product.full.ChooseProductFragment;
import com.anphat.supplier.ui.sms.newsfeed.LoadAdsAdapter;
import com.anphat.supplier.utils.AppConstants;
import com.anphat.supplier.utils.AutoScrollRecyclerView;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.TestConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends BaseFragment implements BranchContract.View, HomeContract.View {
    List<KhoInfo> listDataBranch = new ArrayList<>();
    CategoryMainAdapter adapterCategory;
    RecyclerView rclCategory, rclMain, rclAllProduct;
    DetailAdapter adapter, adapterAllProduct;
    HomePresenter presenterProduct;
    EditText inputSearch;
    ImageView imageDelete;
    private Timer timer;
    AppPreference preference;
    Boolean isBuyNow = false;
    LoadAdsAdapter adsAdapter;
    AutoScrollRecyclerView rclImage;
    LinearLayout layoutConfig;
    TextView textMore,textMoreAllProduct;
    final int SHOW = 1;

    public boolean isAll = false;

    public BaseEventClick.OnClickListener listener;

    public void setClick(BaseEventClick.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        preference = new AppPreference(getActivity());
        rclCategory = bind(view, R.id.rclCategory);
        rclMain = bind(view, R.id.rclMain);
        textMoreAllProduct = bind(view,R.id.textMoreAllProduct);

        textMore = bind(view,R.id.textMore);
        rclAllProduct = bind(view, R.id.rclAllProduct);
        imageDelete = bind(view, R.id.imageDelete);
        rclImage = bind(view, R.id.rclImage);
        layoutConfig = bind(view, R.id.layoutConfig);
        adapterAllProduct = new DetailAdapter(getContext(), new ArrayList<>(), "");
        adapterAllProduct.setAll(false);
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

        rclAllProduct.setAdapter(adapterAllProduct);

        adapter = new DetailAdapter(getActivity(), new ArrayList<>(), "");
        adapter.setAll(false);
        rclMain.setAdapter(adapter);

//        rclMain.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        Search();
        adsAdapter = new LoadAdsAdapter(getContext());
        rclImage.setAdapter(adsAdapter);

//        addAds();
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
            if (AppPreference.getProductPromotion()==null) return;
            StartFullProduct(AppPreference.getProductPromotion());
            Intent intentB = new Intent();
            intentB.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
            intentB.putExtra("eventName", "hh");
            getContext().sendBroadcast(intentB);
        });
        textMoreAllProduct.setOnClickListener(v -> {
            if (AppPreference.getProductFull()==null) return;
            StartFullProduct(AppPreference.getProductFull());
            Intent intentB = new Intent();
            intentB.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
            intentB.putExtra("eventName", "hh");
            getContext().sendBroadcast(intentB);
        });
    }

    private void addAds(List<BannerInfo> list) {
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
//                timer = new Timer();
//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
                        new Handler().postDelayed(() -> {
                            adapter.getFilter().filter(editable);
                            adapterAllProduct.getFilter().filter(editable);
                        },AppConstants.DELAY_FIND_DATA_SEARCH);
//                        getActivity().runOnUiThread(() -> adapter.getFilter().filter(editable));
//                    }
//                }, AppConstants.DELAY_FIND_DATA_SEARCH);
            }
        });

        imageDelete.setOnClickListener(view -> inputSearch.setText(""));
    }

    @Override
    protected void initData() {
        presenterProduct = new HomePresenter(this);
        presenterProduct.GetListBanner("api/slider");
        presenterProduct.GetListBanner("api/banner");

        if (AppPreference.getProductPromotion() == null) {
            presenterProduct.GetListProduct("api/promotions");
        } else {
            adapter.clear();
            adapter.addAll(AppPreference.getProductPromotion());
        }

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

        if (AppPreference.getProductFull()!=null){
            adapterAllProduct.clear();
            adapterAllProduct.addAll(AppPreference.getProductFull());
        } else {
            isAll = true;
            presenterProduct.GetListProduct("api/products");
        }

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
        AppPreference.saveProductPromotion(list);
        adapter.clear();
        adapter.addAll(list);
        if (isAll){
            adapterAllProduct.clear();
            adapterAllProduct.addAll(list);
        }
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

    private void StartDetailFragment(Integer ID) {
        ChooseProductFragment nextFrag = new ChooseProductFragment(ID);
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

}
