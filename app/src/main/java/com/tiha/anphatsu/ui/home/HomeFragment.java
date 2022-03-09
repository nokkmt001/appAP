package com.tiha.anphatsu.ui.home;

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

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tiha.anphatsu.R;
import com.tiha.anphatsu.data.AppPreference;
import com.tiha.anphatsu.data.entities.CategoryInfo;
import com.tiha.anphatsu.data.entities.CategoryNew;
import com.tiha.anphatsu.data.entities.ProductInfo;
import com.tiha.anphatsu.data.entities.ProductNew;
import com.tiha.anphatsu.data.entities.condition.CartCondition;
import com.tiha.anphatsu.data.entities.condition.ProductCondition;
import com.tiha.anphatsu.data.entities.kho.KhoInfo;
import com.tiha.anphatsu.ui.base.BaseEventClick;
import com.tiha.anphatsu.ui.base.BaseFragment;
import com.tiha.anphatsu.ui.booking.BookingActivity;
import com.tiha.anphatsu.ui.home.branch.BranchContract;
import com.tiha.anphatsu.ui.home.branch.BranchPresenter;
import com.tiha.anphatsu.ui.home.choose.FullCategoryActivity;
import com.tiha.anphatsu.ui.product.detail.DetailAdapter;
import com.tiha.anphatsu.ui.product.full.ChooseProductFragment;
import com.tiha.anphatsu.ui.sms.newsfeed.LoadAdsAdapter;
import com.tiha.anphatsu.utils.AppConstants;
import com.tiha.anphatsu.utils.AutoScrollRecyclerView;
import com.tiha.anphatsu.utils.PublicVariables;
import com.tiha.anphatsu.utils.TestConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends BaseFragment implements BranchContract.View, HomeContract.View {
    BranchPresenter presenter;
    List<KhoInfo> listDataBranch = new ArrayList<>();
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

    public BaseEventClick.OnClickListener listener;

    public void setClick(BaseEventClick.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
//        Intent intentMain = new Intent();
//        intentMain.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
//        intentMain.putExtra("eventShow","kk");
//        getContext().sendBroadcast(intentMain);

        showProgressDialog(true);
        preference = new AppPreference(getActivity());
        rclCategory = bind(view, R.id.rclCategory);
        rclMain = bind(view, R.id.rclMain);
        imageDelete = bind(view, R.id.imageDelete);
        rclImage = bind(view, R.id.rclImage);
        layoutConfig = bind(view, R.id.layoutConfig);

        inputSearch = bind(view, R.id.inputSearch);
        adapterCategory = new CategoryMainAdapter();
        rclCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rclCategory.setAdapter(adapterCategory);
        adapterCategory.setOnClickListener((view1, position) -> {
            CategoryNew info = adapterCategory.getItem(position);
            Intent intent = new Intent(getContext(), DetailCategoryActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("CATEGORY", info);
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        adapter = new DetailAdapter(getActivity(), new ArrayList<>(), "");
        rclMain.setAdapter(adapter);
        rclMain.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        Search();
        adsAdapter = new LoadAdsAdapter(getContext());
        rclImage.setAdapter(adsAdapter);

        addAds();
        layoutConfig.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), FullCategoryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, SHOW);
        });
        adapter.setClickListener((view12, position) -> {
            Intent intentB = new Intent();
            intentB.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
            intentB.putExtra("eventShow","hh");
            getContext().sendBroadcast(intentB);
            StartDetailFragment((int) adapter.getItem(position).id);
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
                        getActivity().runOnUiThread(() -> adapter.getFilter().filter(editable));
                    }
                }, AppConstants.DELAY_FIND_DATA_SEARCH);
            }
        });

        imageDelete.setOnClickListener(view -> inputSearch.setText(""));
    }

    @Override
    protected void initData() {
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

        presenterProduct.GetListProduct("api/promotions");

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
        presenterProduct.GetListProduct("api/promotions");
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
        adapter.clear();
        adapter.addAll(list);
        showProgressDialog(false);
    }

    @Override
    public void onGetListProductError(String error) {
        showMessage(error);
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

    private void StartDetailFragment(Integer ID){
        ChooseProductFragment nextFrag= new ChooseProductFragment(ID);
        CommonFM.fragmentTwo = nextFrag;
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container,nextFrag,"one")
                .hide(CommonFM.fragment)
                .addToBackStack(null)
                .commit();
    }

}
