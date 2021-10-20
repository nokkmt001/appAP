package com.tiha.anphat.ui.sms.newsfeed;

import android.view.View;

import com.tiha.anphat.R;
import com.tiha.anphat.databinding.FragmentNewsFeedBinding;
import com.tiha.anphat.ui.base.BaseFragment;

public class NewsFeedFragment extends BaseFragment {
    FragmentNewsFeedBinding binding;

    public NewsFeedFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_feed;
    }

    @Override
    protected void onInit(View view) {
        binding = FragmentNewsFeedBinding.inflate(getLayoutInflater());
        binding.getRoot();
        binding.textMainMain.setText("");
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
}
