package com.anphat.supplier.ui.sms.newsfeed;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.anphat.supplier.data.entities.BannerInfo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.anphat.supplier.R;
import com.anphat.supplier.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class LoadAdsAdapter extends RecyclerView.Adapter<LoadAdsAdapter.MyViewHolder> {
    List<BannerInfo> listAllData;
    Context mContext;

    public LoadAdsAdapter(Context context){
        mContext = context;
        clear();
    }

    public void clear() {
        listAllData = new ArrayList<>();
        listAllData.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<BannerInfo> list) {
        listAllData.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_full, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BannerInfo gg = listAllData.get(position);
        String url = "https://www.gasanphat.com/" + gg.photo;

        Glide.with(mContext)
                .load(url)
                .error(R.drawable.img_no_image)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listAllData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder(@NonNull View view) {
            super(view);
            imageView = bind(view, R.id.imageMain);
        }
    }

    public <T extends View> T bind(View view, int id) {
        return view.findViewById(id);
    }
}
