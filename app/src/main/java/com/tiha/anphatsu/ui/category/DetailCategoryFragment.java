package com.tiha.anphatsu.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tiha.anphatsu.R;
import com.tiha.anphatsu.data.AppPreference;
import com.tiha.anphatsu.data.entities.CategoryInfo;
import com.tiha.anphatsu.data.entities.CategoryNew;
import com.tiha.anphatsu.data.entities.ProductNew;
import com.tiha.anphatsu.data.entities.condition.CartCondition;
import com.tiha.anphatsu.ui.base.BaseFragment;
import com.tiha.anphatsu.ui.home.CategoryMainAdapter;
import com.tiha.anphatsu.ui.home.CommonFM;
import com.tiha.anphatsu.ui.home.DataFilterProduct;
import com.tiha.anphatsu.ui.home.HomeContract;
import com.tiha.anphatsu.ui.home.HomePresenter;
import com.tiha.anphatsu.ui.product.detail.DetailAdapter;
import com.tiha.anphatsu.ui.product.full.ChooseProductActivity;
import com.tiha.anphatsu.utils.AppConstants;
import com.tiha.anphatsu.utils.PublicVariables;
import com.tiha.anphatsu.utils.TestConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DetailCategoryFragment extends BaseFragment implements HomeContract.View {
    DetailAdapter adapter;
    HomePresenter presenterProduct;
    CategoryNew category;
    List<CategoryInfo> listAllData;
    List<ProductNew> listProduct;
    CategoryMainAdapter adapterCategory;
    Boolean isEmpty = false;
    RecyclerView rclMain, rclCategory;
    ImageView imageBack;
    TextView textTitle;
    EditText inputSearch;
    ImageView imageDelete;
    private Timer timer;

    public DetailCategoryFragment(CategoryNew category) {
        this.category = category;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_detail_category;
    }

    @Override
    protected void initView(View view) {
        imageBack = bind(view, R.id.imageBack);
        rclMain = bind(view, R.id.rclMain);
        textTitle = bind(view, R.id.textTitle);
        rclCategory = bind(view, R.id.rclCategory);
        imageBack = bind(view, R.id.imageBack);
        imageBack = bind(view, R.id.imageBack);
        imageBack = bind(view, R.id.imageBack);
        imageBack = bind(view, R.id.imageBack);
        inputSearch = bind(view,R.id.inputSearch);
        imageDelete = bind(view,R.id.imageDelete);

        listProduct = new ArrayList<>();
        showProgressDialog(true);
        imageBack.setOnClickListener(view1 -> {
            Intent intentB = new Intent();
            intentB.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
            intentB.putExtra("eventShow", "kk");
            getContext().sendBroadcast(intentB);

            StartFragmentHome();
        });
        adapter = new DetailAdapter(getContext(), new ArrayList<>(), "");
        rclMain.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rclMain.setAdapter(adapter);

        adapterCategory = new CategoryMainAdapter();
        rclCategory.setAdapter(adapterCategory);
        adapterCategory.clear();
        adapterCategory.setOnClickListener((view1, position) -> {
            CategoryNew item = adapterCategory.getItem(position);
            onFilterProduct(item.id);
        });

        adapter.setClickListener((view1, position) -> {
            Intent intent = new Intent(getContext(), ChooseProductActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle bundle = new Bundle();
            bundle.putInt("ITEM", (int) adapter.getItem(position).id);
            intent.putExtras(bundle);
            startActivity(intent);
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
        listAllData = new ArrayList<>();
        onLoadCategory(category.id);
        presenterProduct.GetListProduct("api/products");
        textTitle.setText(category.title);
    }

    private void onLoadCategory(Float category) {
        List<CategoryNew> list = new ArrayList<>();
        for (CategoryNew item : AppPreference.getCategory()) {
            if (item.parent_id.equals(category)) {
                list.add(item);
            }
        }

        if (list.size() == 0) {
            isEmpty = true;
            return;

        }
        adapterCategory.clear();
        adapterCategory.addAll(list);

    }

    private void onFilterProduct(Float category) {
        listProduct = DataFilterProduct.getList(category);
        PublicVariables.listKM = listProduct;
        adapter.clear();
        adapter.addAll(listProduct);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetListProductSuccess(List<ProductNew> list) {
        DataFilterProduct.list = list;
        showProgressDialog(false);
        adapter.clear();
        List<ProductNew> listAll;
        if (isEmpty) {
            listAll  = DataFilterProduct.getList(category.id);
        } else {
            listAll = DataFilterProduct.getList(adapterCategory.getItem(0).id);
        }
        PublicVariables.listKM = listAll;
        adapter.addAll(listAll);

    }

    @Override
    public void onGetListProductError(String error) {
        showProgressDialog(false);
        showMessage(error);
    }

    @Override
    public void onInsertCartSuccess(CartCondition info) {

    }

    @Override
    public void onInsertCartError(String error) {

    }

    private void StartFragmentHome() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .hide(this)
                .show(CommonFM.fragment)
                .addToBackStack(null)
                .commit();
    }
}
