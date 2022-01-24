package com.tiha.admin.anphat.ui.product;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {
    Context mContext;
    private final List<Fragment> mListFragment = new ArrayList<>();
    private final List<String> mTitleList = new ArrayList<>();

    public PagerAdapter(Context context, @NonNull FragmentManager fm, int tab) {
        super(fm);
        this.mContext = context;
    }

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mListFragment.get(position);
    }

    @Override
    public int getCount() {
        return mListFragment.size();
    }

    public void addFragment(Fragment fragment, String title, int position) {
        mListFragment.add(position, fragment);
        mTitleList.add(position, title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
