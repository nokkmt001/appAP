package com.tiha.admin.anphat.ui.sms.friends;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tiha.admin.anphat.ui.base.BaseFragment;
import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.data.entities.FriendInfo;

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
