package com.tiha.anphat.ui.sms.friends;

import android.view.View;
import android.widget.ImageView;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.FriendInfo;
import com.tiha.anphat.ui.base.BaseAdapter;

public class FriendAdapter extends BaseAdapter<FriendInfo> {
    //TextView ;
    ImageView imageView;

    @Override
    public int getLayoutId() {
        return R.layout.item_chat_friends;
    }

    @Override
    public void bind(View view) {

    }

    @Override
    public void setupViews(View itemView, FriendInfo item, int position) {

    }

    @Override
    public void setupActions(View itemView, FriendInfo item, int position) {

    }
}
