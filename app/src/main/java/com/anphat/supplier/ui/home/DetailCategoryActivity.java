package com.anphat.supplier.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;


import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.CategoryInfo;
import com.anphat.supplier.data.entities.CategoryNew;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.databinding.ActivityDetailCategoryBinding;
import com.anphat.supplier.ui.base.BaseTestActivity;
import com.anphat.supplier.ui.product.detail.DetailAdapter;
import com.anphat.supplier.ui.product.full.ChooseProductActivity;

import java.util.ArrayList;
import java.util.List;

public class DetailCategoryActivity extends BaseTestActivity<ActivityDetailCategoryBinding> implements HomeContract.View {
    ActivityDetailCategoryBinding bd;
    DetailAdapter adapter;
    HomePresenter presenterProduct;
    CategoryNew category = null;
    List<CategoryInfo> listAllData;
    List<ProductNew> listProduct;
    CategoryMainAdapter adapterCategory;
    Boolean isEmpty = false;

    @Override
    public ActivityDetailCategoryBinding getViewBinding() {
        return bd = ActivityDetailCategoryBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        listProduct = new ArrayList<>();
        showProgressDialog(true);
        bd.layoutHeader.imageBack.setOnClickListener(view -> finish());
        adapter = new DetailAdapter(DetailCategoryActivity.this, new ArrayList<>(), "");
        bd.rclMain.setLayoutManager(new GridLayoutManager(this, 2));
        bd.rclMain.setAdapter(adapter);

        adapterCategory = new CategoryMainAdapter();
        bd.rclCategory.setAdapter(adapterCategory);
        adapterCategory.clear();
        adapterCategory.setOnClickListener((view, position) -> {
            CategoryNew item = adapterCategory.getItem(position);
            onFilterProduct(item.id);
        });

        adapter.setClickListener((view1, position) -> {
            Intent intent = new Intent(this, ChooseProductActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle bundle = new Bundle();
            bundle.putInt("ITEM", (int) adapter.getItem(position).id);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }


    @Override
    protected void initData() {
        presenterProduct = new HomePresenter(this);
        listAllData = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        category = (CategoryNew) bundle.getSerializable("CATEGORY");
        onLoadCategory(category.id);
        presenterProduct.GetListProduct("api/products");
        bd.layoutHeader.textTitle.setText(category.title);
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

    private void onFilterProduct(Float category){
        listProduct =  DataFilterProduct.getList(category);
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
        AppPreference.saveProduct(list);
        adapter.clear();
        if (isEmpty){
            adapter.addAll(DataFilterProduct.getList(category.id));
        } else {
            adapter.addAll(DataFilterProduct.getList(adapterCategory.getItem(0).id));
        }

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
}
