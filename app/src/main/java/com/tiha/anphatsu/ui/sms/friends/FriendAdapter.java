package com.tiha.anphatsu.ui.sms.friends;

import android.view.View;
import android.widget.ImageView;

import com.tiha.anphatsu.R;
import com.tiha.anphatsu.data.entities.FriendInfo;
import com.tiha.anphatsu.ui.base.BaseAdapter;

public class FriendAdapter extends BaseAdapter<FriendInfo> {
    //TextView ;
    ImageView imageView;

    @Override
    public int getLayoutId() {
        return R.layout.item_chat_friends;
    }

    @Override
    public void setupViews(View itemView, FriendInfo item, int position) {

    }

    @Override
    public void setupActions(View itemView, FriendInfo item, int position) {

    }
}
