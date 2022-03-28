package com.anphat.supplier.ui.pay;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.anphat.supplier.main.MainActivity;
import com.google.android.material.tabs.TabLayout;
import com.anphat.supplier.R;
import com.anphat.supplier.ui.base.BaseFragment;
import com.anphat.supplier.ui.pay.history.HistoryFragment;
import com.anphat.supplier.ui.pay.pending.PendingFragment;
import com.anphat.supplier.ui.product.PagerAdapter;

public class PayFragment extends BaseFragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter adapterPager;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_pay;
    }

    @Override
    protected void initView(View view) {
        viewPager = bind(view, R.id.viewPager);
        tabLayout = bind(view, R.id.tabLayout);
        setupViewPager();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {

    }

    public void setupViewPager() {
        int tabCount = 1;
        viewPager.setOffscreenPageLimit(tabCount);
        adapterPager = new PagerAdapter(getActivity(), getChildFragmentManager(), tabCount);
        adapterPager.addFragment(new PendingFragment(), getString(R.string.delivering), 0);
        adapterPager.addFragment(new HistoryFragment(), getString(R.string.history), 1);
        viewPager.setAdapter(adapterPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
