package com.anphat.supplier.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;

import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.CategoryNew;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.databinding.ActivityDetailCategoryBinding;
import com.anphat.supplier.ui.base.BaseMainFragment;
import com.anphat.supplier.ui.base.SearchMain;
import com.anphat.supplier.ui.cart.CartActivity;
import com.anphat.supplier.ui.category.DetailCAdapter;
import com.anphat.supplier.ui.product.detail.DetailAdapter;
import com.anphat.supplier.ui.product.full.ChooseProductActivity;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.TestConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class ShowFragment extends BaseMainFragment<ActivityDetailCategoryBinding> {
    DetailAdapter adapter;
    CategoryNew category;
    List<ProductNew> listProduct;
    DetailCAdapter adapterCategory;
    Boolean isEmpty = false;
    private Timer timer;
    List<ProductNew> listData = new ArrayList<>();

    public ShowFragment(List<ProductNew> list) {
        this.listData = list;
    }

    @Override
    protected void initView() {
        binding.rclCategory.setVisibility(View.GONE);
        listProduct = new ArrayList<>();
        showProgressDialog(true);
        binding.layoutHeader.imageBack.setOnClickListener(view1 -> {
            Intent intentB = new Intent();
            intentB.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
            intentB.putExtra("eventName", "kk");
            getContext().sendBroadcast(intentB);

            StartFragmentHome();
        });
        adapter = new DetailAdapter(getContext(), new ArrayList<>(), "");
        adapter.setAll(true);
        binding.rclMain.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.rclMain.setAdapter(adapter);

        adapterCategory = new DetailCAdapter();
        binding.rclCategory.setAdapter(adapterCategory);

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
        binding.layoutHeader.textTitle.addTextChangedListener(new SearchMain() {
            @Override
            protected void onAfterChanged(String text) {
                new Handler().postDelayed(() -> adapter.getFilter().filter(text),1200);
            }
        });

        binding.layoutHeader.layoutCart.layoutClickNo.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), CartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            getContext().startActivity(intent);
        });
    }

    @Override
    public ActivityDetailCategoryBinding getViewBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return ActivityDetailCategoryBinding.inflate(inflater);
    }

    @Override
    protected void initData() {
        PublicVariables.listKM = listData;

        adapter.clear();
        adapter.addAll(listData);
        showProgressDialog(false);
        if (PublicVariables.listBooking!=null){
            binding.layoutHeader.layoutCart.textNumberCart.setText(String.valueOf(PublicVariables.listBooking.size()));
        } else {
            binding.layoutHeader.layoutCart.textNumberCart.setVisibility(View.GONE);
        }
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
        adapterCategory.setSelect_position(0);

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


      /*  DataFilterProduct.list = list;
        showProgressDialog(false);
        adapter.clear();
        List<ProductNew> listAll;
        if (isEmpty) {
            listAll = DataFilterProduct.getList(category.id);
        } else {
            listAll = DataFilterProduct.getList(adapterCategory.getItem(0).id);
        }
        PublicVariables.listKM = listAll;
        adapter.addAll(listAll);*/

    private void StartFragmentHome() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .hide(this)
                .show(CommonFM.fragment)
                .addToBackStack(null)
                .commit();
    }
}
