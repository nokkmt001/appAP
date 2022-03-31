package com.anphat.supplier.utils.adapterimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.anphat.supplier.R;
import com.anphat.supplier.ui.base.BaseEventClick;
import com.anphat.supplier.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ViewImage extends PagerAdapter {
    private BaseEventClick.OnClickListener clickListener;
    private Context mContext;
    LayoutInflater mLayoutInflater;
    List<String> listAllData;

    public ViewImage(Context context, List<String> list) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listAllData = list;
    }

    public void setOnClickListener(BaseEventClick.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        String gg = listAllData.get(position);
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        final TouchImageView imageView = (TouchImageView) itemView.findViewById(R.id.imageView);
        ImageView rlBack = (ImageView) itemView.findViewById(R.id.rlBack);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.onClick(view, position);
                }
            }

        });

        Glide.with(mContext).asBitmap()
                .load(AppUtils.formatStringToBitMap(gg))
                .override(500,500)
                .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
                .apply(new RequestOptions()
                        .placeholder(R.drawable.img_no_image).error(R.drawable.img_no_image))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull @NotNull Bitmap resource, @Nullable @org.jetbrains.annotations.Nullable Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

           /* LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(950, 950);
            imageView.setLayoutParams(layoutParams);*/
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return listAllData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
