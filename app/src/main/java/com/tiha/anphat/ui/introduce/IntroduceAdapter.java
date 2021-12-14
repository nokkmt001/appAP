package com.tiha.anphat.ui.introduce;

import android.content.Context;
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
import com.tiha.anphat.utils.LetterTileProvider;

public class IntroduceAdapter extends BaseTestAdapter<IntroducePerInfo, ItemIntroduceBinding> {
    ItemIntroduceBinding bd;
    private LetterTileProvider mLetterTileProvider;

    public IntroduceAdapter(Context context) {
        this.mLetterTileProvider = new LetterTileProvider(context);
    }

    @Override
    public void setupViews(View itemView, IntroducePerInfo item, int position) {
        bd.textName.setText(item.HoTen);
        bd.imageUser.setImageBitmap(mLetterTileProvider.getLetterTile(item.HoTen));

    }

    @Override
    public ItemIntroduceBinding getViewBinding(ViewGroup parent, int viewType) {
        return bd = ItemIntroduceBinding.inflate(LayoutInflater.from(parent.getContext()));
    }
}
