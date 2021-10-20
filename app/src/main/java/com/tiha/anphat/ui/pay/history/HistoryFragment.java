package com.tiha.anphat.ui.pay.history;

import android.view.View;
import android.widget.TextView;

import com.tiha.anphat.R;
import com.tiha.anphat.ui.base.BaseFragment;

public class HistoryFragment extends BaseFragment {
    TextView textTitle;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    protected void onInit(View view) {
        textTitle = view.findViewById(R.id.textTitle);
        textTitle.setText(R.string.history_title);
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
