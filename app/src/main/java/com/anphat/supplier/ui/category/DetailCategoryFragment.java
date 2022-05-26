package com.anphat.supplier.ui.category;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;

import com.anphat.supplier.R;
import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.CategoryNew;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.databinding.ActivityDetailCategoryBinding;
import com.anphat.supplier.ui.base.BaseMainFragment;
import com.anphat.supplier.ui.cart.CartActivity;
import com.anphat.supplier.ui.home.CommonFM;
import com.anphat.supplier.ui.home.DataFilterProduct;
import com.anphat.supplier.ui.home.HomeFragment;
import com.anphat.supplier.ui.product.detail.DetailAdapter;
import com.anphat.supplier.ui.product.full.ChooseProductFragment;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.TestConstants;

import java.util.ArrayList;
import java.util.List;

public class DetailCategoryFragment extends BaseMainFragment<ActivityDetailCategoryBinding> {
    DetailAdapter adapter;
    CategoryNew category;
    List<ProductNew> listProduct;
    DetailCAdapter adapterCategory;
    Boolean isEmpty = false;
    TestReceiver receiver;

    public DetailCategoryFragment(CategoryNew category) {
        this.category = category;
    }

    @Override
    protected void initView() {

        receiver = new TestReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TestConstants.ACTION_MAIN_HOME);
        getContext().registerReceiver(searchMain, intentFilter);

        listProduct = new ArrayList<>();
        listProduct = AppPreference.getAllProduct();
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
        adapterCategory.clear();
        adapterCategory.setOnClickListener((view1, position) -> {
            CategoryNew item = adapterCategory.getItem(position);
            onFilterProduct(item.id);
        });

        adapter.setClickListener((view1, position) -> {
            StartDetailFragment((int) adapter.getItem(position).id);
            Intent intentB = new Intent();
            intentB.setAction(TestConstants.ACTION_MAIN_ACTIVITY);
            intentB.putExtra("eventName", "hh");
            getContext().sendBroadcast(intentB);
        });
        binding.layoutHeader.layoutCart.layoutClickNo.setOnClickListener(view12 -> {
            Intent intent = new Intent(getContext(), CartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            getContext().startActivity(intent);
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
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                new Handler().postDelayed(() -> adapter.getFilter().filter(editable.toString()), 1000);
            }
        });
    }

    @Override
    public ActivityDetailCategoryBinding getViewBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return ActivityDetailCategoryBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initData() {
        onLoadCategory(category.id);
        if (PublicVariables.listBooking != null && PublicVariables.listBooking.size() > 0) {
            binding.layoutHeader.layoutCart.textNumberCart.setVisibility(View.VISIBLE);
            binding.layoutHeader.layoutCart.textNumberCart.setText(String.valueOf(PublicVariables.listBooking.size()));
        } else {
            binding.layoutHeader.layoutCart.textNumberCart.setVisibility(View.GONE);
        }
        DataFilterProduct.list = listProduct;
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

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getContext(), "resume", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getContext(), "Pause", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        Toast.makeText(getContext(), "stop", Toast.LENGTH_LONG).show();
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

    private void StartFragmentHome() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .hide(this)
                .replace(R.id.frame_container, new HomeFragment(), "two")
                .addToBackStack(null)
                .commit();
    }

    private void StartDetailFragment(Integer ID) {
        ChooseProductFragment nextFrag = new ChooseProductFragment(ID, false);
        CommonFM.fragmentTwo = nextFrag;
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container, nextFrag, "one")
//                .hide(CommonFM.fragmentThree)
                .hide(this)
                .addToBackStack(null)
                .commit();
    }

    private class TestReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle == null) return;
            String eventName = bundle.getString("eventName");
            switch (eventName) {
                case TestConstants.RECEIVE_ThayDoiGioHang:
                    break;
                default:
                    break;
            }
        }
    }
}
