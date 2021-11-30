package com.tiha.anphat.ui.introduce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.IntroducePerInfo;
import com.tiha.anphat.databinding.ItemIntroduceBinding;
import com.tiha.anphat.ui.base.BaseAdapter;
import com.tiha.anphat.ui.base.BaseTestAdapter;

public class IntroduceAdapter extends BaseTestAdapter<IntroducePerInfo, ItemIntroduceBinding> {
    ItemIntroduceBinding bd;
    @Override
    public void setupViews(View itemView, IntroducePerInfo item, int position) {
        bd.textName.setText(item.HoTen);
    }

    @Override
    public ItemIntroduceBinding getViewBinding(ViewGroup parent, int viewType) {
        return bd = ItemIntroduceBinding.inflate(LayoutInflater.from(parent.getContext()));
    }
}
