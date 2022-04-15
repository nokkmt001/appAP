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
    private LetterTileProvider mLetterTileProvider;

    public IntroduceAdapter(Context context) {
        this.mLetterTileProvider = new LetterTileProvider(context);
    }

    @Override
    public void setupViews(ItemIntroduceBinding binding, IntroducePerInfo item, int position) {
        binding.textName.setText(item.HoTen);
        binding.imageUser.setImageBitmap(mLetterTileProvider.getLetterTile(item.HoTen));

    }

    @Override
    public ItemIntroduceBinding getViewBinding(ViewGroup parent, int viewType) {
        return  ItemIntroduceBinding.inflate(LayoutInflater.from(parent.getContext()));
    }
}
