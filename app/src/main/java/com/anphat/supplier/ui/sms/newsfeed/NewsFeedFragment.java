package com.anphat.supplier.ui.sms.newsfeed;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.anphat.supplier.R;
import com.anphat.supplier.ui.base.BaseFragment;
import com.anphat.supplier.utils.AutoScrollRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedFragment extends BaseFragment {
    ImageView imageMain;
    AutoScrollRecyclerView rclImage;
    LoadAdsAdapter adsAdapter;
    public NewsFeedFragment() {
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_news_feed;
    }

    @Override
    protected void initView(View view) {
        imageMain = bind(view, R.id.imageMain);
        rclImage = bind(view,R.id.rclImage);
        adsAdapter = new LoadAdsAdapter(getContext());
        String url = "https://i.ibb.co/ZTVvwRc/gas-test.png";
        rclImage.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rclImage.setAdapter(adsAdapter);
    }

    @Override
    protected void initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i<10;i++){
            list.add("jjjhhh");
        }
        adsAdapter.clear();
//        adsAdapter.addAll(list);
        rclImage.startAutoScroll();
        rclImage.setLoopEnabled(true);

    }

    @Override
    public void onClick(View view) {

    }
}
