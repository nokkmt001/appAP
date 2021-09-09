package com.tiha.anphat.ui.sms;

import android.view.View;
import android.widget.RelativeLayout;

import com.tiha.anphat.R;
import com.tiha.anphat.ui.base.BaseFragment;

public class SmsFragment extends BaseFragment {
    RelativeLayout layoutHeader;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sms;
    }

    @Override
    protected void onInit(View view) {
        layoutHeader = view.findViewById(R.id.layoutHeader);
        layoutHeader.setBackgroundResource(R.color.colorTransparent);
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
