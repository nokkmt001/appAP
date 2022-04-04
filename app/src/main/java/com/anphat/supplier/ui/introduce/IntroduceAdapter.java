package com.anphat.supplier.ui.introduce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anphat.supplier.data.entities.IntroducePerInfo;
import com.anphat.supplier.databinding.ItemIntroduceBinding;
import com.anphat.supplier.ui.base.BaseTestAdapter;
import com.anphat.supplier.utils.LetterTileProvider;

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