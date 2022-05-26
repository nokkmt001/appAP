package com.anphat.supplier.ui.sms.newsfeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.BannerInfo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ViewImageAds extends PagerAdapter {
    public Context mContext;
    LayoutInflater mLayoutInflater;
    List<BannerInfo> listAllData;

    public ViewImageAds(Context context, List<BannerInfo> list) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listAllData = list;
    }

    public void AddAll(List<BannerInfo> list) {
        listAllData.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_image_full, container, false);
        final ImageView imageView = itemView.findViewById(R.id.imageMain);
        BannerInfo gg = listAllData.get(position);
        String url = "https://www.gasanphat.com/" + gg.photo;

        Glide.with(mContext)
                .load(url)
                .error(R.drawable.img_no_image)
                .into(imageView);

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
