package com.anphat.supplier.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.BannerInfo;
import com.anphat.supplier.data.entities.CategoryInfo;
import com.anphat.supplier.data.entities.CategoryNew;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.condition.CartCondition;
import com.anphat.supplier.databinding.ActivityDetailCategoryBinding;
import com.anphat.supplier.ui.base.BaseMainFragment;
import com.anphat.supplier.ui.cart.CartActivity;
import com.anphat.supplier.ui.category.DetailCAdapter;
import com.anphat.supplier.ui.product.detail.DetailAdapter;
import com.anphat.supplier.ui.product.full.ChooseProductActivity;
import com.anphat.supplier.utils.AppConstants;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.TestConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ShowFragment extends BaseMainFragment<ActivityDetailCategoryBinding> implements HomeContract.View {
    DetailAdapter adapter;
    HomePresenter presenterProduct;
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
        binding.layoutHeader.textTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
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
        presenterProduct = new HomePresenter(this);
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

    @Override
    public void onGetListProductSuccess(List<ProductNew> list) {
        DataFilterProduct.list = list;
        showProgressDialog(false);
        adapter.clear();
        List<ProductNew> listAll;
        if (isEmpty) {
            listAll = DataFilterProduct.getList(category.id);
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

    @Override
    public void onGetListBannerSuccess(List<BannerInfo> list) {

    }

    @Override
    public void onGetListBannerError(String error) {

    }

    @Override
    public void onGetListProductPromotionSuccess(List<ProductNew> list) {

    }

    @Override
    public void onGetListProductPromotionError(String error) {

    }

    private void StartFragmentHome() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .hide(this)
                .show(CommonFM.fragment)
                .addToBackStack(null)
                .commit();
    }
}
