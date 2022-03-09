package com.tiha.anphatsu.ui.pay.history.vote;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tiha.anphatsu.R;
import com.tiha.anphatsu.ui.base.BaseEventClick;
import com.tiha.anphatsu.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AddImageAdapter extends RecyclerView.Adapter<AddImageAdapter.ItemRowHolder> {
    private BaseEventClick.OnClickListener clickListener;
    Context mContext;
    ArrayList<String> listAllData;

    public AddImageAdapter(Context context, ArrayList<String> list) {
        this.listAllData = list;
        this.mContext = context;
    }

    public void setOnClickListener(BaseEventClick.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void addAll(List<String> serviceReportResults) {
        listAllData.addAll(serviceReportResults);
        notifyDataSetChanged();
    }

    public void add(String itemAdd) {
        listAllData.add(itemAdd);
        if (listAllData.size() == 0) {
            notifyItemInserted(1);
        } else notifyItemInserted(listAllData.size() - 1);
    }

    public void clear() {
        listAllData = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        listAllData.remove(position);
        notifyDataSetChanged();
    }

    public ArrayList<String> getListAllData() {
        return listAllData;
    }

    public String getItem(int position) {
        return listAllData.get(position);
    }

    @Override
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_add_image, viewGroup, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NotNull final ItemRowHolder holder, int position) {
        String hinhAnhInfo = listAllData.get(position);
        if (!hinhAnhInfo.equals("")) {
            Glide.with(mContext).asBitmap()
                    .load(AppUtils.formatStringToBitMap(hinhAnhInfo))
                    .apply(new RequestOptions().override(10, 10))
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull @NotNull Bitmap resource, @Nullable @org.jetbrains.annotations.Nullable Transition<? super Bitmap> transition) {
                            holder.imageAdd.setImageBitmap(resource);

                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return (null != listAllData ? listAllData.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        ImageView imageDelete, imageAdd;
        RelativeLayout layoutColor;

        public ItemRowHolder(View view) {
            super(view);
            this.layoutColor = view.findViewById(R.id.layoutMain);
            this.imageAdd = view.findViewById(R.id.imageAdd);
            this.imageDelete = view.findViewById(R.id.imageDelete);
            this.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onClick(v, getAdapterPosition());
                    }
                }


            });
            this.imageAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onClick(v, getAdapterPosition());
                    }
                }


            });
        }
    }
}