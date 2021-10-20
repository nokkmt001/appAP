package com.tiha.anphat.ui.sms.friends;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.FriendInfo;
import com.tiha.anphat.ui.base.BaseAdapter;

public class FriendAdapter extends BaseAdapter<FriendInfo> {
    //TextView ;
    ImageView imageView;

    public FriendAdapter(int layoutId, final Callback callback,
                         SwipeRefreshLayout refreshLayout) {
        super(layoutId, callback, refreshLayout);
    }

    @Override
    public void bindView(View itemView) {
//        imageView = bind(itemView, R.id.)
    }

    @Override
    public void setupViews(View itemView, FriendInfo item) {

    }

    @Override
    public void setupActions(View itemView, FriendInfo item, int position) {

    }
}
