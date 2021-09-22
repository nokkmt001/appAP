package com.tiha.anphat.ui.product;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tiha.anphat.R;
import com.tiha.anphat.ui.base.BaseFragment;
import com.tiha.anphat.ui.product.detail.DetailProductFragment;

public class ProductFragment extends BaseFragment {
    ProductPresenter presenter;
    TabLayout tabLayout;
    ViewPager viewPager;
    ProductPagerAdapter adapterPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product;
    }

    @Override
    protected void onInit(View view) {
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        setupViewPager();
    }

    @Override
    protected void onLoadData() {
    }

    @Override
    protected void configToolbar() {

    }

    @Override
    public void onClick(View view) {

    }

    public void setupViewPager(){
        int tabCount = 1;
        viewPager.setOffscreenPageLimit(tabCount);
        adapterPager = new ProductPagerAdapter(getActivity(),getChildFragmentManager(), tabCount);
        adapterPager.addFragment(new DetailProductFragment("PHUKIEN"),getString(R.string.accessory),0);
        adapterPager.addFragment(new DetailProductFragment("BRN_2715GN"),getString(R.string.kitchen),1);
        adapterPager.addFragment(new DetailProductFragment("PCCC"),getString(R.string.fire_fighting),2);
        adapterPager.addFragment(new DetailProductFragment("VO"),getString(R.string.bark),3);
        adapterPager.addFragment(new DetailProductFragment("BBINH"),getString(R.string.vase_set),4);
        adapterPager.addFragment(new DetailProductFragment("GAO"),getString(R.string.rice),5);
        adapterPager.addFragment(new DetailProductFragment(getString(R.string.gas)),getString(R.string.gas),6);
        adapterPager.addFragment(new DetailProductFragment("LUA"),getString(R.string.paddy),7);
        adapterPager.addFragment(new DetailProductFragment("NEP"),"NẾP",8);
        adapterPager.addFragment(new DetailProductFragment("TAM"),"TẮM",9);
        adapterPager.addFragment(new DetailProductFragment("TA"),"THỨC ĂN",10);
        adapterPager.addFragment(new DetailProductFragment("KHUYENMAI"),getString(R.string.promotion),11);
        viewPager.setAdapter(adapterPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }
}
