package com.anphat.supplier.ui.product.full;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.gift.GiftInfo;
import com.anphat.supplier.ui.base.BaseLMAdapter;
import com.bumptech.glide.Glide;

public class GiftAdapter extends BaseLMAdapter<GiftInfo, GiftAdapter.ItemRowHolder> {
    Context mContext;
    int select_position = -1;

    public GiftAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected int onBindLayout() {
        return R.layout.item_gift;
    }

    @Override
    protected ItemRowHolder onCreateHolder(View view) {
        return new ItemRowHolder(view);
    }

    public void undoCheck(){
        select_position = -1;
        notifyDataSetChanged();
    }

    @Override
    protected void onBindData(ItemRowHolder row, GiftInfo item, int position) {
        row.textDetail.setText(item.title);
        String url = "https://gasanphat.com/" + item.photo;
        Glide.with(mContext)
                .load(url)
                .error(R.drawable.img_no_image)
                .override(300, 300)
                .into(row.imageGift);

        row.checkbox.setChecked(select_position == position);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        CheckBox checkbox;
        ImageView imageGift;
        TextView textDetail;

        public ItemRowHolder(@NonNull View view) {
            super(view);
            checkbox = bind(view, R.id.checkbox);
            imageGift = bind(view, R.id.imageGift);
            textDetail = bind(view, R.id.textDetail);

            checkbox.setOnClickListener(view1 -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mItemClickListener.onItemClick(getItem(position), view1, position);
                    select_position = position;
                    notifyDataSetChanged();
                }
            });
        }
    }

}
