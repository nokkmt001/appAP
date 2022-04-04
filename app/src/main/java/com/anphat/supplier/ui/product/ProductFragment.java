package com.anphat.supplier.ui.product;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.CategoryInfo;
import com.anphat.supplier.ui.base.BaseFragment;
import com.anphat.supplier.ui.product.detail.DetailProductFragment;
import com.anphat.supplier.utils.PublicVariables;

import java.util.List;

public class ProductFragment extends BaseFragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter adapterPager;
    List<CategoryInfo> list;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_product;
    }

    @Override
    protected void initView(View view) {
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

    }

    @Override
    protected void initData() {
        list = PublicVariables.listCategory;
        setupViewPager();
    }

    @Override
    public void onClick(View view) {

    }

    public void setupViewPager(){
        int tabCount = 2;
        viewPager.setOffscreenPageLimit(tabCount);
        adapterPager = new PagerAdapter(getActivity(),getChildFragmentManager(), tabCount);
        int position = 0;
        for (CategoryInfo item:list){
            adapterPager.addFragment(new DetailProductFragment(item.getCategory_ID()),item.getLoaihang(),position);
            position+=1;
        }
        viewPager.setAdapter(adapterPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }
}