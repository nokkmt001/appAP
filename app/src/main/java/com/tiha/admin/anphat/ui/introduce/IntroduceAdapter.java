package com.tiha.admin.anphat.ui.introduce;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiha.admin.anphat.data.entities.IntroducePerInfo;
import com.tiha.admin.anphat.ui.base.BaseAdapter;
import com.tiha.admin.anphat.R;

public class IntroduceAdapter extends BaseAdapter<IntroducePerInfo> {
    ImageView imageMain;
    TextView textName,textSdt,textAddress;
    @Override
    public int getLayoutId() {
        return R.layout.item_introduce;
    }

    @Override
    public void bind(View view) {
        imageMain = bind(view,R.id.imageMain);
        textSdt = bind(view,R.id.textSdt);
        textName = bind(view,R.id.textName);
        textAddress = bind(view,R.id.textAddress);
    }

    @Override
    public void setupViews(View itemView, IntroducePerInfo item, int position) {
        textName.setText(item.HoTen);
    }

    @Override
    public void setupActions(View itemView, IntroducePerInfo item, int position) {
    }
}
