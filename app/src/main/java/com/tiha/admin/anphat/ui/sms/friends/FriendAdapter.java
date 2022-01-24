package com.tiha.admin.anphat.ui.sms.friends;

import android.view.View;
import android.widget.ImageView;

import com.tiha.admin.anphat.ui.base.BaseAdapter;
import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.data.entities.FriendInfo;

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
