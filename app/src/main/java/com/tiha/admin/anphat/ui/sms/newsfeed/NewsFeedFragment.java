package com.tiha.admin.anphat.ui.sms.newsfeed;

import android.view.View;

import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.databinding.FragmentNewsFeedBinding;
import com.tiha.admin.anphat.ui.base.BaseFragment;

public class NewsFeedFragment extends BaseFragment {
    FragmentNewsFeedBinding binding;

    public NewsFeedFragment() {
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_news_feed;
    }

    @Override
    protected void initView(View view) {
        binding = FragmentNewsFeedBinding.inflate(getLayoutInflater());
        binding.getRoot();
        binding.textMainMain.setText("");
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {

    }
}
