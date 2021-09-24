package com.tiha.anphat.ui.sms;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tiha.anphat.R;
import com.tiha.anphat.ui.base.BaseFragment;
import com.tiha.anphat.ui.product.PagerAdapter;

public class SmsFragment extends BaseFragment {
    RelativeLayout layoutHeader;
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter adapterPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sms;
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

    public void setupViewPager() {
        int tabCount = 1;
        viewPager.setOffscreenPageLimit(tabCount);
        adapterPager = new PagerAdapter(getActivity(), getChildFragmentManager(), tabCount);
        viewPager.setAdapter(adapterPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }
}
