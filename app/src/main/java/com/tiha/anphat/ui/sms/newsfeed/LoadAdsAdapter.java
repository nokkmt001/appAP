package com.tiha.anphat.ui.sms.newsfeed;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tiha.anphat.R;
import com.tiha.anphat.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class LoadAdsAdapter extends RecyclerView.Adapter<LoadAdsAdapter.MyViewHolder> {
    List<String> listAllData;
    Context mContext;

    public LoadAdsAdapter(Context context){
        mContext = context;
    }

    public void clear() {
        listAllData = new ArrayList<>();
        listAllData.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<String> list) {
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
        String gg = listAllData.get(position);
        String url = "https://i.ibb.co/RyDV8B4/images.jpg";

        Glide.with(mContext)
                .load(url)
                .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.imageView.setImageBitmap(AppUtils.drawableToBitmap(resource));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
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
