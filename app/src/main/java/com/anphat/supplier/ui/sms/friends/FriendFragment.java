package com.anphat.supplier.ui.sms.friends;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.FriendInfo;
import com.anphat.supplier.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class FriendFragment extends BaseFragment {
    List<FriendInfo> list = new ArrayList<>();
    FriendAdapter adapter;
    public FriendFragment() {
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_friends;
    }

    @Override
    protected void initView(View view) {
        RecyclerView rcl = bind(view,R.id.recyclerViewChat);
        SwipeRefreshLayout refresh_layout = bind(view,R.id.refresh_layout);
         adapter = new FriendAdapter();

        rcl.setAdapter(adapter);

    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View view) {

    }
}
