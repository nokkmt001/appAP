package com.tiha.anphat.ui.sms.friends;

import android.os.Handler;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.FriendInfo;
import com.tiha.anphat.ui.base.BaseAdapter;
import com.tiha.anphat.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class FriendFragment extends BaseFragment implements BaseAdapter.Callback {
    List<FriendInfo> list = new ArrayList<>();
    FriendAdapter adapter;
    public FriendFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_friends;
    }

    @Override
    protected void initView(View view) {
        RecyclerView rcl = bind(view,R.id.recyclerViewChat);
        SwipeRefreshLayout refresh_layout = bind(view,R.id.refresh_layout);
         adapter = new FriendAdapter(R.layout.item_chat_friends,this,refresh_layout);

        rcl.setAdapter(adapter);

    }

    @Override
    protected void initData() {
//        listAllData = AppUtils.getContacts(getActivity());
    }

    @Override
    protected void configToolbar() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRefreshListener() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.initData(list);
            }
        }, 0);
    }

    @Override
    public void onLoadMoreListener(int newPage) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.addData(list);
            }
        }, 0);

    }
}
