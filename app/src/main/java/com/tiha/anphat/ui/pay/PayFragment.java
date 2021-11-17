package com.tiha.anphat.ui.pay;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tiha.anphat.R;
import com.tiha.anphat.ui.base.BaseFragment;
import com.tiha.anphat.ui.pay.history.HistoryFragment;
import com.tiha.anphat.ui.product.PagerAdapter;
import com.tiha.anphat.ui.sms.friends.FriendFragment;
import com.tiha.anphat.ui.sms.newsfeed.NewsFeedFragment;

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
    protected void configToolbar() {

    }

    @Override
    public void onClick(View view) {

    }

    public void setupViewPager() {
        int tabCount = 1;
        viewPager.setOffscreenPageLimit(tabCount);
        adapterPager = new PagerAdapter(getActivity(), getChildFragmentManager(), tabCount);
        adapterPager.addFragment(new FriendFragment(), getString(R.string.delivering), 0);
        adapterPager.addFragment(new HistoryFragment(), getString(R.string.history), 1);
        viewPager.setAdapter(adapterPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
